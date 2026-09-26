package example.queue

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.xiajun.data.singlePriorityTask.PrioritizedTask
import com.xiajun.data.singlePriorityTask.SinglePriorityTaskManager
import com.xiajun.app.MyApp.databinding.ActivityQueueTestBinding
import com.xiajun.myevent.EventReceiver
import com.xiajun.myevent.MyEventManager
import java.util.*

class QueueTestActivity : AppCompatActivity(), EventReceiver {

    private lateinit var bd: ActivityQueueTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bd = ActivityQueueTestBinding.inflate(layoutInflater)
        setContentView(bd.root)
        bd.btnRegister.setOnClickListener {
            MyEventManager.getInstance().register(this, "00")
        }
        bd.btnUnregister.setOnClickListener {
            MyEventManager.getInstance().unRegister(this)
        }
        bd.btnSendEvent.setOnClickListener {
            MyEventManager.getInstance().post("00", bd.tvContent.text.toString())
        }
        val random = Random()
        bd.btnPriorityQueue.setOnClickListener {
            for (i in 0..10) {
                val t = PrioritizedTask(i.toString(), random.nextInt(50))
                SinglePriorityTaskManager.getInstance().addTask(t.id, t)
            }
        }
    }

    override fun onMainThreadEvent(code: String, data: Any) {
        if (code == "00") {
            val s = data as String
            Log.e("data==", s + "")
            bd.tvResult.text = s
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        MyEventManager.getInstance().unRegister(this)
    }
}
