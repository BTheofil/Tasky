package hu.tb.tasky.ui.task_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.tb.tasky.data.repository.DataStoreProtoRepository
import hu.tb.tasky.data.repository.TaskEntityRepositoryImpl
import hu.tb.tasky.model.ListEntity
import hu.tb.tasky.ui.add_edit_task.alarm.AlarmScheduler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val taskEntityRepository: TaskEntityRepositoryImpl,
    private val scheduler: AlarmScheduler,
    dataStoreProto: DataStoreProtoRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(TaskListState())
    val state: StateFlow<TaskListState> = _state

    val dataStore: DataStoreProtoRepository = dataStoreProto

    init {
        viewModelScope.launch {
            dataStoreProto.appSettings.collect{
                val result = taskEntityRepository.getAllListsEntityWithTask(it.sortBy, it.sortTYPE)
                _state.value = state.value.copy(
                    listEntityList = result,
                )
            }
        }
    }

    fun onEvent(event: TaskListEvent) {
        when (event) {
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
                    val result = taskEntityRepository.getAllListsEntityWithTask(event.oder, event.orderType)
                    _state.value = state.value.copy(
                        listEntityList = result
                    )
                }
            }
            TaskListEvent.OnAddListClick -> {
                viewModelScope.launch {
                    taskEntityRepository.insertListEntity(ListEntity(name = state.value.newListName))
                }
            }
            is TaskListEvent.OnCreateNewListTextChange -> _state.value =
                state.value.copy(newListName = event.name)
        }
    }
}