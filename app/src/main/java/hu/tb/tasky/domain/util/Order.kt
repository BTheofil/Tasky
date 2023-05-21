package hu.tb.tasky.domain.util

import kotlinx.serialization.Serializable

@Serializable
data class AppSettings(
    val sortBy: Order = Order.TIME,
    val sortTYPE: OrderType = OrderType.DESCENDING,
    val isFirstTimeAppStart: Boolean = true,
)

enum class Order {
    TITLE, TIME,
}

enum class OrderType(val value: Boolean) {
    ASCENDING(true),
    DESCENDING(false)
}