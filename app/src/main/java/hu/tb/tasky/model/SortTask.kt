package hu.tb.tasky.model

import hu.tb.tasky.domain.util.Sort

data class SortTask(
    val name: String,
    val onClick: (Sort) -> Unit
)
