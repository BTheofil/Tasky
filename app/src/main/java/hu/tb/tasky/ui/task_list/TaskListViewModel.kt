package hu.tb.tasky.ui.task_list

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.tb.tasky.data.repository.TaskRepositoryImpl
import hu.tb.tasky.model.Task
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(
    mockTask: TaskRepositoryImpl,
) : ViewModel() {

    private val _taskList = mutableStateListOf(
        Task(
            title = "Test",
            description = "Something more about the task...",
            expireDate = null,
            expireTime = null,
            isDone = false),
    )
    val taskList: List<Task> = mockTask.getTaskList()

    fun onTaskIsDoneChange(index: Int, newValue: Boolean){
        _taskList[index] = _taskList[index].copy(isDone = newValue)
    }
}