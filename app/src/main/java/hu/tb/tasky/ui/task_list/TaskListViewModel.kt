package hu.tb.tasky.ui.task_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.tb.tasky.data.repository.TaskEntityRepository
import hu.tb.tasky.model.TaskEntity
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val realRepository: TaskEntityRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val ongoingTaskList = savedStateHandle.getStateFlow<List<TaskEntity>>(ONGOING_TASK_KEY, emptyList())
    val doneTaskList = savedStateHandle.getStateFlow<List<TaskEntity>>(DONE_TASK_KEY, emptyList())

    init {
        viewModelScope.launch {
            realRepository.getDoneTaskEntities().collect{
                savedStateHandle[DONE_TASK_KEY] = it
            }
        }
        viewModelScope.launch {
            realRepository.getOngoingTaskEntities().collect {
                savedStateHandle[ONGOING_TASK_KEY] = it
            }
        }
    }

    fun onEvent(event: TaskListEvent){
        when(event){
            is TaskListEvent.OnDoneClick -> {
                viewModelScope.launch {
                    realRepository.insertTaskEntity(
                        event.task.copy(
                            isTaskDone = event.isDone
                        )
                    )
                }
            }
        }
    }

    companion object{
        const val ONGOING_TASK_KEY = "OngoingTaskEntities"
        const val DONE_TASK_KEY = "DoneTaskEntities"
    }

}