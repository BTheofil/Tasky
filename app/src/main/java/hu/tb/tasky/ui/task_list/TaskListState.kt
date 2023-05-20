package hu.tb.tasky.ui.task_list

import hu.tb.tasky.model.TaskEntity
import hu.tb.tasky.model.relations.ListWithTask

data class TaskListState(
    val taskMapList: Map<Int, List<TaskEntity>> = emptyMap(),
    val listEntityList: List<ListWithTask> = emptyList(),
    val newListName: String = "",
)