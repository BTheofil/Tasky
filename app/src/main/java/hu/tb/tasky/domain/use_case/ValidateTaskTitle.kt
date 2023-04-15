package hu.tb.tasky.domain.use_case

import hu.tb.tasky.ui.add_edit_task.AddEditTaskState

class ValidateTaskTitle {

    fun execute(task: AddEditTaskState): ValidationResult =
        if (task.title.isNotEmpty()) {
            ValidationResult.SUCCESS
        } else {
            ValidationResult.ERROR
        }
}