package hu.tb.tasky.ui.task_list

import android.util.Log
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
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val allTaskList = savedStateHandle.getStateFlow<List<TaskEntity>>(ALL_TASK_KEY, emptyList())

    init {
        viewModelScope.launch {
            realRepository.getTaskEntities().collect {
                savedStateHandle[ALL_TASK_KEY] = it
            }
        }
    }

    fun onEvent(event: TaskListEvent){
        when(event){
            is TaskListEvent.OnDeleteClick -> {
                viewModelScope.launch {
                    realRepository.deleteTask(event.task)
                }
            }
            is TaskListEvent.ShowDoneTask -> {
                viewModelScope.launch {
                    realRepository.getDoneTaskEntities().collect {
                        Log.d("MYTAG", it.size.toString())
                    }
                }
            }
            is TaskListEvent.OnDoneClick -> {
                viewModelScope.launch {
                    realRepository.insertTaskEntity(
                        event.task.copy(
                            initialChecked = event.isDone
                        )
                    )
                }
            }
        }
    }

    companion object{
        const val ALL_TASK_KEY = "TaskEntity"
    }

}