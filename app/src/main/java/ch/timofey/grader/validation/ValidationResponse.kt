package ch.timofey.grader.validation

data class ValidationResponse(
    val isValid: Boolean,
    val errorMessage: String = ""
)
