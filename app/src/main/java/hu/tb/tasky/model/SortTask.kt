package hu.tb.tasky.model

data class SortTask(
    val name: String,
    val onClick: () -> Unit
)
