package hu.tb.tasky.ui.add_edit_task.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import hu.tb.tasky.model.Task
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId

class AlarmScheduler(
    private val context: Context
) {
    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    fun schedule(item: Task) {
        //todo outsource
        if(item.expireDate == null && item.expireTime == null){
            return
        }

        val intent = Intent(context, AlarmReceiver::class.java)
            .putExtra(INTENT_EXPIRE_TASK_KEY, item)

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            LocalDateTime.of(
                item.expireDate!!.year,
                item.expireDate.monthValue - 1,
                item.expireDate.dayOfMonth,
                item.expireTime!!.hour,
                item.expireTime.minute,
                0,
            ).atZone(ZoneId.systemDefault()).toEpochSecond() * 1000,
            PendingIntent.getBroadcast(
                context,
                item.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    fun cancel(item: Task) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                item.hashCode(),
                Intent(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    companion object {
        const val INTENT_EXPIRE_TASK_KEY = "EXPIRE_TASK"
    }
}