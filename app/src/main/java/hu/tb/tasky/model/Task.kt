package hu.tb.tasky.model

import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

data class Task(
    val title: String,
    val description: String,
    val expireDate: LocalDate?,
    val expireTime: LocalTime?,
    val isDone: Boolean,
)
