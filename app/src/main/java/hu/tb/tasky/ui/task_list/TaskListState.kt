package hu.tb.tasky.ui.task_list

import hu.tb.tasky.model.ListEntity
import hu.tb.tasky.model.relations.ListWithTask

data class TaskListState(
    val listEntityWithTaskAllList: List<ListWithTask> = emptyList(),
    val newListName: String = "",
    val activeListEntity: ListEntity = ListEntity(1, "My list"),
    val createNewListDialogHasError: Boolean = false,
)