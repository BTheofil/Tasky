package hu.tb.tasky.domain.use_case

class ValidateTitle {

    fun execute(title: String): ValidationResult =
        if (title.isNotEmpty()) {
            ValidationResult.SUCCESS
        } else {
            ValidationResult.ERROR
        }
}