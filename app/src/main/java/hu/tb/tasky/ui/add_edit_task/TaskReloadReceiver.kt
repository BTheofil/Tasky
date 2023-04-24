package hu.tb.tasky.ui.add_edit_task

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import hu.tb.tasky.data.repository.TaskEntityEntityRepositoryImpl
import hu.tb.tasky.domain.use_case.ValidateDateTime
import hu.tb.tasky.domain.use_case.ValidationResult
import hu.tb.tasky.model.TaskEntity
import hu.tb.tasky.ui.add_edit_task.alarm.AlarmScheduler
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class TaskReloadReceiver : BroadcastReceiver() {

    @Inject
    lateinit var taskEntityEntityRepository: TaskEntityEntityRepositoryImpl

    @Inject
    lateinit var scheduler: AlarmScheduler

    private val validator = ValidateDateTime()

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            runBlocking {
                launch {
                    taskEntityEntityRepository.getOngoingTaskEntities().collect { onGoingTaskList ->
                        onGoingTaskList.forEach { task ->
                            if (validator.execute(converter(task)) == ValidationResult.SUCCESS) {
                                scheduler.schedule(task)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun converter(task: TaskEntity): AddEditTaskState = with(task) {
        AddEditTaskState(
            id = this.id,
            title = this.title,
            description = this.description,
            expireDate = this.expireDate,
            expireTime = this.expireTime,
            initialChecked = this.isTaskDone,
            isTitleError = false,
            isDateTimeError = false,
        )
    }
}

