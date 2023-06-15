package hu.tb.tasky.ui.task_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.tb.tasky.data.repository.DataStoreProtoRepository
import hu.tb.tasky.data.repository.TaskEntityRepositoryImpl
import hu.tb.tasky.domain.use_case.ValidateTitle
import hu.tb.tasky.domain.use_case.ValidationResult
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
    private val dataStoreProto: DataStoreProtoRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(TaskListState())
    val state: StateFlow<TaskListState> = _state

    val dataStore: DataStoreProtoRepository = dataStoreProto

    init {
        viewModelScope.launch {
            dataStore.appSettings.collect { appSettings ->
                if (appSettings.isFirstTimeAppStart) {
                    taskEntityRepository.insertListEntity(ListEntity(name = "My list"))
                    dataStore.setFirstTimeAppStartToFalse()
                }
            }
        }

        viewModelScope.launch {
            dataStoreProto.appSettings.collect { appSettings ->
                taskEntityRepository.getAllListsEntityWithTask(
                    appSettings.sortOrder,
                    appSettings.sortType
                ).collect { allListWithTask ->
                    _state.value = state.value.copy(
                        listEntityWithTaskAllList = allListWithTask,
                        activeListEntity = allListWithTask[0].list
                    )
                }
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
                    dataStoreProto.saveSort(event.oder, event.orderType)
                }

                viewModelScope.launch {
                    taskEntityRepository.getAllListsEntityWithTask(event.oder, event.orderType)
                        .collect {
                            _state.value = state.value.copy(
                                listEntityWithTaskAllList = it
                            )
                        }
                }
            }

            is TaskListEvent.OnCreateNewListTextChange -> _state.value =
                state.value.copy(newListName = event.name)

            is TaskListEvent.OnListDelete -> {
                viewModelScope.launch {
                    val listToRemove = state.value.listEntityWithTaskAllList
                        .filter { it.list == event.listEntity }
                        .flatMap { it.listOfTask }

                    listToRemove.forEach { taskEntity ->
                        scheduler.cancel(taskEntity.taskId!!)
                        taskEntityRepository.deleteTask(taskEntity)
                    }
                    taskEntityRepository.deleteListEntity(event.listEntity.listId)
                }
            }

            is TaskListEvent.ChangeActiveList -> _state.value =
                state.value.copy(activeListEntity = event.listEntity)

            is TaskListEvent.SaveList -> {
                val titleResult = ValidateTitle().execute(_state.value.newListName)

                if (titleResult == ValidationResult.ERROR) {
                    _state.value = state.value.copy(
                        createNewListDialogHasError = true
                    )
                    return
                }
                viewModelScope.launch {
                    taskEntityRepository.insertListEntity(ListEntity(name = _state.value.newListName))
                    _state.value = state.value.copy(newListName = "")
                }
                _state.value = state.value.copy(
                    createNewListDialogHasError = false
                )
            }

            is TaskListEvent.ClearDialogState -> _state.value = state.value.copy(
                createNewListDialogHasError = false,
                newListName = ""
            )
        }
    }
}