package ch.timofey.grader.db.domain.exam

import ch.timofey.grader.validation.Validatable
import ch.timofey.grader.validation.Validate
import ch.timofey.grader.validation.ValidationResponse

object ExamValidation : Validatable<ExamValidationFields> {
    fun name(name: String): ValidationResponse {
        return when {
            name.isBlank() -> ValidationResponse(valid = false, message = "Name is Required")
            name.length > 60 -> ValidationResponse(
                valid = false, message = "Name cannot be over 60 characters long"
            )

            else -> ValidationResponse(valid = true)
        }
    }

    fun description(description: String): ValidationResponse {
        return ValidationResponse(valid = true)
    }

    fun grade(grade: String, useSwiss: Boolean = false): ValidationResponse {
        if (grade.isBlank()) {
            return ValidationResponse(valid = false, message = "Grade is Required")
        }
        if (!Validate.isDouble(grade)) {
            return ValidationResponse(valid = false, message = "Enter a Valid Decimal number")
        }
        if (useSwiss) {
            if (!Validate.swissGrade(grade.toDouble())) {
                return ValidationResponse(valid = false, message = "Enter a valid Swiss Grade")
            }
        }
        return ValidationResponse(valid = true)
    }

    fun weight(weight: String, usePercentage: Boolean = false): ValidationResponse {
        if (weight.isBlank()) {
            return ValidationResponse(valid = false, message = "Weight is Required")
        }
        if (usePercentage) {
            if (!Validate.fullNumber(weight)) {
                return ValidationResponse(
                    valid = false, message = "Weight has to be a valid Number"
                )
            }
            if (!Validate.inRange(weight.toInt(), 1, 100)) {
                return ValidationResponse(
                    valid = false, message = "The Weight has to be a valid percentage"
                )
            }
        } else {
            if (!Validate.isDouble(weight)) {
                return ValidationResponse(
                    valid = false, message = "Weight has to be a valid Number"
                )
            }
            if (!Validate.inRange(weight.toDouble(), 0.0, 1.0)) {
                return ValidationResponse(
                    valid = false, message = "Weight has to be between 0 and 1"
                )
            }
        }
        return ValidationResponse(true)
    }

    private fun dateTaken(date: String): ValidationResponse {
        return when {
            date.isBlank() -> ValidationResponse(valid = false, message = "Date Taken is Required")
            !Validate.date(date) -> ValidationResponse(
                valid = false, message = "The Date is not formatted correctly"
            )

            else -> ValidationResponse(true)
        }
    }

    override fun validateAll(fields: ExamValidationFields): Boolean {
        val name = name(fields.name).valid
        val description = description(fields.description).valid
        val grade = grade(fields.grade).valid
        val weight = weight(fields.weight).valid
        val dateTaken = dateTaken(fields.dateTaken).valid
        return name && description && grade && weight && dateTaken
    }

}