package hu.tb.tasky.ui.task_list

import hu.tb.tasky.domain.util.Order
import hu.tb.tasky.domain.util.OrderType
import hu.tb.tasky.model.ListEntity
import hu.tb.tasky.model.TaskEntity

sealed class TaskListEvent{
    data class OnDoneClick(val task: TaskEntity, val isDone: Boolean): TaskListEvent()
    data class OnSortButtonClick(val oder: Order, val orderType: OrderType): TaskListEvent()
    object OnAddListClick: TaskListEvent()
    data class OnCreateNewListTextChange(val name: String): TaskListEvent()
    data class OnListDelete(val listEntity: ListEntity): TaskListEvent()
    data class ChangeActiveList(val listEntity: ListEntity): TaskListEvent()
}
