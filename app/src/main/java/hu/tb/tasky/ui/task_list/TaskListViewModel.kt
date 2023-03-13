package hu.tb.tasky.ui.task_list

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.tb.tasky.data.repository.TaskRepositoryImpl
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(
    mockTask: TaskRepositoryImpl,
) : ViewModel() {

    private val _allTasks = mockTask.getTaskList()
    val taskList = _allTasks
}