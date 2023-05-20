package hu.tb.tasky.ui.add_edit_task

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.tb.tasky.data.repository.TaskEntityRepositoryImpl
import hu.tb.tasky.domain.use_case.ValidateDateTime
import hu.tb.tasky.domain.use_case.ValidateTaskTitle
import hu.tb.tasky.model.TaskEntity
import hu.tb.tasky.ui.add_edit_task.alarm.AlarmScheduler
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class AddEditTaskViewModel @Inject constructor(
    private val scheduler: AlarmScheduler,
    savedStateHandle: SavedStateHandle,
    private val taskEntityEntityRepository: TaskEntityRepositoryImpl,
) : ViewModel() {

    private val _task = mutableStateOf(
        AddEditTaskState(
            title = "",
            description = "",
            expireDate = null,
            expireTime = null,
            initialChecked = false,
            isTitleError = false,
            isDateTimeError = false
        )
    )
    val task: State<AddEditTaskState> = _task

    init {
        viewModelScope.launch {
            savedStateHandle.get<Int>("editedTask")?.let { taskId ->
                taskEntityEntityRepository.getTaskEntityById(taskId)?.also {
                    _task.value = task.value.copy(
                        id = it.taskId,
                        title = it.title,
                        description = it.description,
                        expireDate = it.expireDate,
                        expireTime = it.expireTime,
                    )
                }
            }
        }
    }

    fun onEvent(event: AddEditTaskEvent) {
        when (event) {
            is AddEditTaskEvent.OnTitleChange -> _task.value = task.value.copy(title = event.title)
            is AddEditTaskEvent.OnDescriptionChange -> _task.value =
                task.value.copy(description = event.description)
            is AddEditTaskEvent.OnDateChange -> {
                if (task.value.id != null) {
                    scheduler.cancel(task.value.id!!)
                }
                _task.value = task.value.copy(
                    expireDate = LocalDate.parse(
                        event.date.toString(),
                        DateTimeFormatter.ISO_LOCAL_DATE
                    )
                )
            }
            is AddEditTaskEvent.OnTimeChange -> {
                if (task.value.id != null) {
                    scheduler.cancel(task.value.id!!)
                }
                _task.value = task.value.copy(
                    expireTime = LocalTime.parse(
                        event.time.toString(),
                        DateTimeFormatter.ISO_LOCAL_TIME
                    )
                )
            }
            is AddEditTaskEvent.OnDeleteClick -> {
                if (task.value.id != null) {
                    scheduler.cancel(task.value.id!!)
                }
                viewModelScope.launch {
                    taskEntityEntityRepository.deleteTask(converter(_task.value))
                }
            }
            is AddEditTaskEvent.OnClearDateTimeClick -> {
                if (task.value.id != null) {
                    scheduler.cancel(task.value.id!!)
                }
                _task.value = task.value.copy(
                    expireDate = null,
                    expireTime = null,
                )
            }
        }
    }

    fun save(): Boolean {

        val titleResult = ValidateTaskTitle().execute(_task.value)
        val dateTimeResult = ValidateDateTime().execute(_task.value)

        val hasError = listOf(
            titleResult,
            dateTimeResult
        ).any { !it.result }

        if (hasError) {
            _task.value = task.value.copy(
                isTitleError = !titleResult.result,
                isDateTimeError = !dateTimeResult.result,
            )
            return false
        }

        viewModelScope.launch {
            val savedTaskId = taskEntityEntityRepository.insertTaskEntity(converter(_task.value))
            if (_task.value.expireDate != null) {
                scheduler.schedule(converter(_task.value.copy(id = savedTaskId.toInt())))
            }
        }
        return true
    }


    private fun converter(task: AddEditTaskState): TaskEntity = TaskEntity(
        taskId = task.id,
        title = task.title,
        description = task.description,
        expireTime = task.expireTime,
        expireDate = task.expireDate,
        isTaskDone = task.initialChecked,
        listId = 1
    )

}