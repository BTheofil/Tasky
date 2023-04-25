package hu.tb.tasky.ui.task_list

import hu.tb.tasky.model.TaskEntity

data class TaskListState(
    val ongoingTaskList: List<TaskEntity>,
    val doneTaskList: List<TaskEntity>,
)