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
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val saveState = savedStateHandle.getStateFlow<List<TaskEntity>>("TaskEntity", emptyList())

    init {
        viewModelScope.launch {
            realRepository.getTaskEntities().collect {
                savedStateHandle["TaskEntity"] = it
            }
        }
    }

}