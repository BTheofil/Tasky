package hu.tb.tasky.ui.task_list

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import hu.tb.tasky.model.Task
import java.util.*

class TaskListViewModel : ViewModel() {

    private val _taskList = mutableStateListOf(
        Task(title = "Test",
            description = "Something more about the task...",
            expireDate = "", //todo handle date
            isDone = false),
    )
    val taskList: List<Task> = _taskList

    fun onTaskIsDoneChange(index: Int, newValue: Boolean){
        _taskList[index] = _taskList[index].copy(isDone = newValue)
    }
}