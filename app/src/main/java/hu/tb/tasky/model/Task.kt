package hu.tb.tasky.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

data class Task(
    val title: String,
    val description: String,
    val expireDate: LocalDate?,
    val expireTime: LocalTime?,
    val initialChecked: Boolean,
) {
    var isDone by mutableStateOf(initialChecked)
}
