package example.editLayout

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiajun.app.MyApp.databinding.EditLayoutMoveBottomOfFullScreenBinding
import com.xiajun.base.BaseTitleBarActivity
import com.xiajun.helper.InputMethodStateListener
import com.xiajun.view.diviver.DividerItemDecoration
import example.adapter.StringAdapter

class FullScreenBottomEditLayoutActivity : BaseTitleBarActivity() {
    lateinit var bd: EditLayoutMoveBottomOfFullScreenBinding
    lateinit var dataList: ArrayList<String>
    lateinit var adapter: StringAdapter

    override fun onCreate(arg0: Bundle?) {
        super.onCreate(arg0)
        bd = EditLayoutMoveBottomOfFullScreenBinding.inflate(layoutInflater)
        setContentView(bd.getRoot())
        setTitleBarTitle("调整编辑布局")
        dataList = ArrayList()
        adapter = StringAdapter(this, dataList)
        val layoutManager = LinearLayoutManager(this)
        bd.recyclerView.setLayoutManager(layoutManager)
        bd.recyclerView.addItemDecoration(DividerItemDecoration(this))
        bd.recyclerView.setAdapter(adapter)
        adapter.setOnItemClickListener { position, data ->
        }
        this.data
        addListener()
    }

    private fun addListener() {
        bd.getRoot().getViewTreeObserver()
            .addOnGlobalLayoutListener(object : InputMethodStateListener(bd.layoutRoot) {
                override fun onInputMethodClosed() {
                }

                override fun onInputMethodOpened(invisibleHeight: Int) {
                }
            })
    }

    private val data: Unit
        get() {
            for (i in 0..2) {
                dataList.add(i.toString() + "")
            }
            adapter.notifyDataSetChanged()
        }
}
