package hu.tb.tasky.ui.add_edit_task

import android.app.Application
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.tb.tasky.alarm.AlarmScheduler
import hu.tb.tasky.data.repository.TaskRepositoryImpl
import hu.tb.tasky.model.Task
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class AddEditTaskViewModel @Inject constructor(
    private val mockRepositoryImpl: TaskRepositoryImpl,
    private val application: Application,
) : ViewModel() {

    private val _task = mutableStateOf(
        Task(
            title = "",
            description = "",
            expireDate = null,
            expireTime = null,
            initialChecked = false,
        )
    )
    val task: State<Task> = _task

    fun onEvent(event: AddEditTaskEvent){
        when(event){
            is AddEditTaskEvent.OnTitleChange -> {
                _task.value = task.value.copy(title = event.title)
            }
            is AddEditTaskEvent.OnDescriptionChange -> {
                _task.value = task.value.copy(description = event.description)
            }
            is AddEditTaskEvent.OnDateChange -> {
                _task.value = task.value.copy(expireDate = LocalDate.parse(event.date.toString(), DateTimeFormatter.ISO_LOCAL_DATE))
            }
            is AddEditTaskEvent.OnTimeChange -> {
                _task.value = task.value.copy(expireTime = LocalTime.parse(event.time.toString(), DateTimeFormatter.ISO_LOCAL_TIME))
            }
            is AddEditTaskEvent.Save -> {
                val scheduler = AlarmScheduler(application)
                scheduler.schedule(_task.value)
                mockRepositoryImpl.addTask(_task.value)
            }
        }
    }

}