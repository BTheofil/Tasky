package hu.tb.tasky.ui.task_list

import hu.tb.tasky.data.util.Sort
import hu.tb.tasky.model.TaskEntity

sealed class TaskListEvent{
    data class OnDoneClick(val task: TaskEntity, val isDone: Boolean): TaskListEvent()
    data class OnSortButtonClick(val sort: Sort): TaskListEvent()
}
