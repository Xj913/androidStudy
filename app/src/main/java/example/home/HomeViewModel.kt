package example.home

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.xiajun.base.BaseCompoModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

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
}

data class Myitem(var id: Int)