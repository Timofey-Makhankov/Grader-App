package ch.timofey.grader.validation

data class ValidationResponse(
    val valid: Boolean,
    val message: String = ""
)
