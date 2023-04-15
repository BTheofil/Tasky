package hu.tb.tasky.ui.add_edit_task

import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

sealed class AddEditTaskEvent {
    data class OnTitleChange(val title: String) : AddEditTaskEvent()
    data class OnDescriptionChange(val description: String) : AddEditTaskEvent()
    data class OnDateChange(val date: LocalDate) : AddEditTaskEvent()
    data class OnTimeChange(val time: LocalTime) : AddEditTaskEvent()
    object OnDeleteClick : AddEditTaskEvent()
}
