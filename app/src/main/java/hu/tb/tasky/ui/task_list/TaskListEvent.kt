package hu.tb.tasky.ui.task_list

import hu.tb.tasky.model.TaskEntity

sealed class TaskListEvent{
    data class OnDoneClick(val task: TaskEntity, val isDone: Boolean): TaskListEvent()
}
