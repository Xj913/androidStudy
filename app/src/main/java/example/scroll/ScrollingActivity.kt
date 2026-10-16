package example.scroll

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.xiajun.app.MyApp.databinding.ActivityScrollingBinding

class ScrollingActivity : AppCompatActivity() {

    private lateinit var bd: ActivityScrollingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bd = ActivityScrollingBinding.inflate(layoutInflater)
        setContentView(bd.root)

    }
}
