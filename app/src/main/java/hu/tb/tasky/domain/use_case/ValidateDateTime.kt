package hu.tb.tasky.domain.use_case

import hu.tb.tasky.ui.add_edit_task.AddEditTaskState
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

class ValidateDateTime {

    fun execute(task: AddEditTaskState): ValidationResult {
        if(task.expireDate != null && task.expireTime != null){
            if(task.expireTime.isAfter(LocalTime.now())){
                return ValidationResult.SUCCESS
            } else if(task.expireDate.isAfter(LocalDate.now())){
                return ValidationResult.SUCCESS
            } else if(task.expireDate.isEqual(LocalDate.now()) && task.expireTime.isAfter(LocalTime.now())){
                return ValidationResult.SUCCESS
            }
        }
        if(task.expireDate == null && task.expireTime == null){
            return ValidationResult.SUCCESS
        }
        return ValidationResult.ERROR
    }

}