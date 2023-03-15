package hu.tb.tasky.ui.add_edit_task

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.tb.tasky.data.repository.TaskEntityRepository
import hu.tb.tasky.ui.add_edit_task.alarm.AlarmScheduler
import hu.tb.tasky.data.repository.TaskRepositoryImpl
import hu.tb.tasky.model.Task
import hu.tb.tasky.model.TaskEntity
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class AddEditTaskViewModel @Inject constructor(
    private val mockRepositoryImpl: TaskRepositoryImpl,
    private val scheduler: AlarmScheduler,
    savedStateHandle: SavedStateHandle,
    private val realRepository: TaskEntityRepository,
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

    init {
        savedStateHandle.get<String>("editedTask")?.let { taskTitle ->
            mockRepositoryImpl.getTaskById(taskTitle)?.also {
                _task.value = task.value.copy(
                    title = it.title,
                    description = it.description,
                    expireDate = it.expireDate,
                    expireTime = it.expireTime,
                )
            }
        }
    }

    fun onEvent(event: AddEditTaskEvent) {
        when (event) {
            is AddEditTaskEvent.OnTitleChange -> {
                _task.value = task.value.copy(title = event.title)
            }
            is AddEditTaskEvent.OnDescriptionChange -> {
                _task.value = task.value.copy(description = event.description)
            }
            is AddEditTaskEvent.OnDateChange -> {
                _task.value = task.value.copy(
                    expireDate = LocalDate.parse(
                        event.date.toString(),
                        DateTimeFormatter.ISO_LOCAL_DATE
                    )
                )
            }
            is AddEditTaskEvent.OnTimeChange -> {
                _task.value = task.value.copy(
                    expireTime = LocalTime.parse(
                        event.time.toString(),
                        DateTimeFormatter.ISO_LOCAL_TIME
                    )
                )
            }
            is AddEditTaskEvent.Save -> {
                scheduler.schedule(_task.value)
                viewModelScope.launch {
                    realRepository.insertTaskEntity(converter(_task.value))
                }
            }
        }
    }

    private fun converter(task: Task): TaskEntity = TaskEntity(
        title = task.title,
        description = task.description,
        expireTime = task.expireTime,
        expireDate = task.expireDate,
        initialChecked = task.initialChecked
    )

}