package example.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.xiajun.ui.StatusBarHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        StatusBarHelper.setup2edge(this)
        super.onCreate(savedInstanceState)
        setContent {
            HomeApp()
        }
    }
}