package hu.tb.tasky.ui.add_edit_task.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import hu.tb.tasky.model.Task
import hu.tb.tasky.ui.add_edit_task.alarm.AlarmScheduler.Companion.INTENT_EXPIRE_TASK_KEY
import hu.tb.tasky.ui.notification.TaskNotificationService

class AlarmReceiver: BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onReceive(context: Context?, intent: Intent?) {
        val expireTask = intent?.getParcelableExtra(INTENT_EXPIRE_TASK_KEY, Task::class.java) ?: return
        if (context != null) {
            val service = TaskNotificationService(context)
            service.showNotification(expireTask)
        }
    }
}