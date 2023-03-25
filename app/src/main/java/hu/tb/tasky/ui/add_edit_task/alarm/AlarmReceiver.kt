package hu.tb.tasky.ui.add_edit_task.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import hu.tb.tasky.model.Task
import hu.tb.tasky.ui.add_edit_task.alarm.AlarmScheduler.Companion.INTENT_EXPIRE_TASK_KEY
import hu.tb.tasky.ui.notification.TaskNotificationService

class AlarmReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val expireTask = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.getParcelableExtra(INTENT_EXPIRE_TASK_KEY, Task::class.java) ?: return
        } else {
            intent?.getParcelableExtra(INTENT_EXPIRE_TASK_KEY) ?: return
        }
        if (context != null) {
            val service = TaskNotificationService(context)
            service.showNotification(expireTask)
        }
    }
}