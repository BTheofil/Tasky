package hu.tb.tasky.ui.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import hu.tb.tasky.MainActivity
import hu.tb.tasky.R
import hu.tb.tasky.model.TaskEntity

class TaskNotificationService(
    private val context: Context
) {
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showNotification(task: TaskEntity) {
        val activityIntent = Intent(context, MainActivity::class.java) //define what activity open when user tap the notification
        val activityPendingIntent = PendingIntent.getActivity(
            context,
            OPEN_MAIN_ACTIVITY,
            activityIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(context, TASK_CHANNEL_ID)
            .setSmallIcon(R.drawable.outline_alarm_24)
            .setContentTitle(task.title)
            .setContentIntent(activityPendingIntent)
            .build()

        notificationManager.notify(task.id!!, notification)
    }

    companion object {
        const val TASK_CHANNEL_ID = "task_channel"
        const val OPEN_MAIN_ACTIVITY = 1
    }
}