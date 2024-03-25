package ch.timofey.grader.db.domain.division

import ch.timofey.grader.validation.Validatable
import ch.timofey.grader.validation.Validate
import ch.timofey.grader.validation.ValidationResponse

object DivisionValidation: Validatable<DivisionValidationFields> {
    fun name(name: String): ValidationResponse {
        return when {
            name.isBlank() -> ValidationResponse(valid = false, message = "Name is Required")
            name.length > 60 -> ValidationResponse(valid = false, message = "Name cannot be over 60 characters long")
            else -> ValidationResponse(valid = true)
        }
    }
    @Suppress("UNUSED_PARAMETER")
    fun description(description: String): ValidationResponse {
        return ValidationResponse(valid = true)
    }
    fun year(year: String): ValidationResponse {
        return when {
            year.length > 9 -> ValidationResponse(valid = false, message = "exceeded length of 9 characters")
            !Validate.year(year) -> ValidationResponse(valid = false, message = "Has to be a valid year")
            else -> ValidationResponse(valid = true)
        }
    }
    override fun validateAll(fields: DivisionValidationFields): Boolean {
        val validName = name(fields.name).valid
        val validDescription = description(fields.description).valid
        val validYear = year(fields.year).valid
        return validName && validDescription && validYear
    }
}