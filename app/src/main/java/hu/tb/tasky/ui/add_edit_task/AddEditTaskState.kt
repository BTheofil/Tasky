package hu.tb.tasky.ui.add_edit_task

import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

data class AddEditTaskState(
    val id: Int? = null,
    val title: String,
    val description: String,
    val expireDate: LocalDate?,
    val expireTime: LocalTime?,
    val initialChecked: Boolean,
    val isTitleError: Boolean,
    val isDateTimeError: Boolean,
)
