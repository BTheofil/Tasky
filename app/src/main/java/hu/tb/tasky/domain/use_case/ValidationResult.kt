package hu.tb.tasky.domain.use_case

enum class ValidationResult(val result: Boolean) {
    SUCCESS(true),
    ERROR(false),
}