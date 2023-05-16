package hu.tb.tasky.ui.task_list

import hu.tb.tasky.model.ListEntity
import hu.tb.tasky.model.TaskEntity
import hu.tb.tasky.model.relations.ListWithTask

data class TaskListState(
    val taskMapList: Map<Int, List<TaskEntity>>,
    val listNamesList: List<ListEntity> = listOf(ListEntity(name = "test")),
    val listWithTask: List<ListWithTask> = emptyList()
)