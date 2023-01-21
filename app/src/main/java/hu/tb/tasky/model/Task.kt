package hu.tb.tasky.model

import java.util.*

data class Task(
    val title: String,
    val description: String,
    val expireDate: String,
    val isDone: Boolean,
)
