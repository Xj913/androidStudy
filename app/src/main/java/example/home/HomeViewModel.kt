package example.home

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.xiajun.base.BaseCompoModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class HomeViewModel @Inject constructor() : BaseCompoModel() {
    private val titles = arrayOf("View", "列表", "手势", "原生", "其他")
    val title = mutableStateOf(titles[0])
    val currentPage = mutableIntStateOf(0)

    fun setPageIndex(i: Int) {
        if (currentPage.intValue == i)
            return
        this.currentPage.intValue = i
    }


    val mList = mutableStateListOf<Myitem>()
    private var pageNo: Int = 1
    val mRefreshState = mutableStateOf(RefreshState.RefreshedHasMoreData)

    fun refresh() {
        mRefreshState.value = RefreshState.Refreshing
        pageNo = 1
        viewModelScope.launch(context = Dispatchers.IO) {
            delay(1500L)
            val t = async { getData(pageNo) }.await()
            withContext(Dispatchers.Main) {
                if (t.isEmpty())
                    mRefreshState.value = RefreshState.RefreshedNoData
                else {
                    mList.clear()
                    mList.addAll(t)
                    mRefreshState.value = RefreshState.RefreshedHasMoreData
                }
            }
        }
    }

    fun loadMore() {
        mRefreshState.value = RefreshState.LoadingMore
        pageNo++
        viewModelScope.launch(context = Dispatchers.IO) {
            delay(1500L)
            val t = async { getData(pageNo) }.await()
            withContext(Dispatchers.Main) {
                if (t.isEmpty())
                    mRefreshState.value = RefreshState.LoadedMoreNoMoreData
                else {
                    mList.addAll(t)
                    mRefreshState.value = RefreshState.LoadedMoreHasMoreData
                }
            }
        }
    }

    fun getData(pageNo: Int): ArrayList<Myitem> {
        val list = arrayListOf<Myitem>()
        if (pageNo == 4)
            return list
        for (i in 0 until 5) {
            list.add(Myitem(i))
        }
        return list
    }
}

data class Myitem(var id: Int)

enum class RefreshState {
    Refreshing,
    LoadingMore,
    RefreshedNoData,
    RefreshedHasMoreData,
    LoadedMoreNoMoreData,
    LoadedMoreHasMoreData,
}