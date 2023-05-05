package hu.tb.tasky.ui.task_list

import hu.tb.tasky.model.TaskEntity

data class TaskListState(
    val taskMapList: Map<Int, List<TaskEntity>>,
)