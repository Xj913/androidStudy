package example.customView

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.style.base.BaseTitleBarActivity
import com.style.app.MyApp.databinding.CustomViewMainBinding
import example.customView.fragment.*

class CustomViewMainActivity : BaseTitleBarActivity() {
    private lateinit var bd: CustomViewMainBinding
    private lateinit var fAdapter: CustomViewFragmentAdapter
    private val fragments = ArrayList<androidx.fragment.app.Fragment>()
    private val titles = ArrayList<String>()

    override fun onCreate(arg0: Bundle?) {
        super.onCreate(arg0)
        bd = CustomViewMainBinding.inflate(layoutInflater)
        setContentView(bd.root)
        setTitleBarTitle("tabLayout")
        titles.add("自定义饼状图")
        fragments.add(PieChartFragment())
        titles.add("自定义通知小圆点")
        fragments.add(CustomNotifyViewFragment())
        titles.add("圆环进度条")
        fragments.add(CircleProgressBarFragment())
        titles.add("波浪球")
        fragments.add(WaterPoloFragment())
        titles.add("声波")
        fragments.add(SoundWaveFragment())
        titles.add("水平进度")
        fragments.add(HorizontalProgressFragment())
        titles.add("扫描")
        fragments.add(ScanViewFragment())
        fAdapter = CustomViewFragmentAdapter(this, fragments)
        bd.viewPager.adapter = fAdapter
        TabLayoutMediator(bd.tabLayout, bd.viewPager) { tab, position ->
            tab.text = "标题 ${position + 1}"
        }.attach()
        bd.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }

}
