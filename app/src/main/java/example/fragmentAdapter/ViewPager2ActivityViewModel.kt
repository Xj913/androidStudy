package example.fragmentAdapter

import com.xiajun.base.BaseViewModel

class ViewPager2ActivityViewModel : BaseViewModel() {

    fun getData(): ArrayList<String> {
        var datas = arrayListOf<String>()
        for (i in 0 until 10) {
            val s = i.toString()
            datas.add(s)
        }
        return datas
    }



}