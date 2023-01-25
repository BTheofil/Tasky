package hu.tb.tasky.model

import org.threeten.bp.ZonedDateTime

data class Task(
    val title: String,
    val description: String,
    val expireDate: ZonedDateTime,
    val isDone: Boolean,
)
