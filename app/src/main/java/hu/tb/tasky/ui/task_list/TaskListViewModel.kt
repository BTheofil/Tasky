package hu.tb.tasky.ui.task_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.tb.tasky.data.repository.TaskEntityRepositoryImpl
import hu.tb.tasky.model.TaskEntity
import hu.tb.tasky.ui.add_edit_task.alarm.AlarmScheduler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val taskEntityRepository: TaskEntityRepositoryImpl,
    private val savedStateHandle: SavedStateHandle,
    private val scheduler: AlarmScheduler,
) : ViewModel() {

    private val _state = mutableStateOf(
        TaskListState(
            ongoingTaskList = emptyList(),
            doneTaskList = emptyList(),
        )
    )
    val state: State<TaskListState> = _state

    init {
        viewModelScope.launch {
            taskEntityRepository.getDoneTaskEntities().collect {
                savedStateHandle[DONE_TASKS_KEY] = it
                updateState()
            }
        }
        viewModelScope.launch{
            taskEntityRepository.getOngoingTaskEntities().collect {
                savedStateHandle[ONGOING_TASKS_KEY] = it
                updateState()
            }
        }
    }

    fun onEvent(event: TaskListEvent){
        when(event){
            is TaskListEvent.OnDoneClick -> {
                viewModelScope.launch {
                    val taskId = taskEntityRepository.insertTaskEntity(
                        event.task.copy(
                            isTaskDone = event.isDone,
                            expireDate = null,
                            expireTime = null,
                        )
                    )
                    scheduler.cancel(taskId.toInt())
                }
            }
            is TaskListEvent.OnSortButtonClick -> {
                viewModelScope.launch {
                    taskEntityRepository.getDoneTaskEntities(event.sort).collect{
                        savedStateHandle[DONE_TASKS_KEY] = it
                        updateState()
                    }
                }
                viewModelScope.launch{
                    taskEntityRepository.getOngoingTaskEntities(event.sort).collect {
                        savedStateHandle[ONGOING_TASKS_KEY] = it
                        updateState()
                    }
                }
            }
        }
    }

    private fun updateState(){
        _state.value = state.value.copy(
            ongoingTaskList = savedStateHandle.getStateFlow<List<TaskEntity>>(ONGOING_TASKS_KEY, emptyList()).value,
            doneTaskList = savedStateHandle.getStateFlow<List<TaskEntity>>(DONE_TASKS_KEY, emptyList()).value,
        )
    }

    companion object{
        const val ONGOING_TASKS_KEY = "OngoingTaskEntities"
        const val DONE_TASKS_KEY = "DoneTaskEntities"
    }

}