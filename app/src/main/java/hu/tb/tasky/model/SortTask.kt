package hu.tb.tasky.model

import hu.tb.tasky.domain.util.Order
import hu.tb.tasky.domain.util.OrderType

data class SortTask(
    val name: String,
    val onClick: (Order, OrderType) -> Unit
)
