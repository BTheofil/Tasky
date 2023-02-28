package hu.tb.tasky.ui.task_list

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.tb.tasky.data.repository.TaskRepositoryImpl
import hu.tb.tasky.model.Task
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(
    mockTask: TaskRepositoryImpl,
) : ViewModel() {

    private val _allTasks = mockTask.getTaskList()
    val taskList = _allTasks

    fun onTaskIsDoneChange(editedTask: Task, newValue: Boolean) {
        taskList.find { it == editedTask }?.let { task ->
            task.isDone = newValue
        }
    }
}