package hu.tb.tasky.ui.add_edit_task

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import hu.tb.tasky.model.Task
import org.threeten.bp.ZonedDateTime

class AddEditTaskViewModel : ViewModel() {

    private val _task = mutableStateOf(
        Task(
            title = "",
            description = "",
            expireDate = ZonedDateTime.parse("2022-01-01T12:00:00Z"),
            isDone = false
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
        }
    }

}