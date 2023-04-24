package hu.tb.tasky.ui.task_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.tb.tasky.data.repository.TaskEntityEntityRepositoryImpl
import hu.tb.tasky.model.TaskEntity
import hu.tb.tasky.ui.add_edit_task.alarm.AlarmScheduler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val taskEntityEntityRepository: TaskEntityEntityRepositoryImpl,
    private val savedStateHandle: SavedStateHandle,
    private val scheduler: AlarmScheduler,
) : ViewModel() {

    val ongoingTaskList = savedStateHandle.getStateFlow<List<TaskEntity>>(ONGOING_TASK_KEY, emptyList())
    val doneTaskList = savedStateHandle.getStateFlow<List<TaskEntity>>(DONE_TASK_KEY, emptyList())

    init {
        viewModelScope.launch {
            taskEntityEntityRepository.getDoneTaskEntities().collect{
                savedStateHandle[DONE_TASK_KEY] = it
            }
        }
        viewModelScope.launch {
            taskEntityEntityRepository.getOngoingTaskEntities().collect {
                savedStateHandle[ONGOING_TASK_KEY] = it
            }
        }
    }

    fun onEvent(event: TaskListEvent){
        when(event){
            is TaskListEvent.OnDoneClick -> {
                viewModelScope.launch {
                    val taskId = taskEntityEntityRepository.insertTaskEntity(
                        event.task.copy(
                            isTaskDone = event.isDone,
                            expireDate = null,
                            expireTime = null,
                        )
                    )
                    scheduler.cancel(taskId.toInt())
                }
            }
        }
    }

    companion object{
        const val ONGOING_TASK_KEY = "OngoingTaskEntities"
        const val DONE_TASK_KEY = "DoneTaskEntities"
    }

}