package ch.timofey.grader.db.domain.module

import ch.timofey.grader.validation.Validatable
import ch.timofey.grader.validation.ValidationResponse

object ModuleValidation: Validatable<ModuleValidationFields> {
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

    fun teacherFirstName(teacherFirstName: String): ValidationResponse {
        return when {
            teacherFirstName.isBlank() -> ValidationResponse(valid = false, message = "Teacher First Name is Required")
            teacherFirstName.length > 60 -> ValidationResponse(valid = false, message = "Teacher First Name cannot be over 60 characters long")
            else -> ValidationResponse(valid = true)
        }
    }

    fun teacherLastName(teacherLastName: String): ValidationResponse {
        return when {
            teacherLastName.isBlank() -> ValidationResponse(valid = false, message = "Teacher Last Name is Required")
            teacherLastName.length > 60 -> ValidationResponse(valid = false, message = "Teacher Last Name cannot be over 60 characters long")
            else -> ValidationResponse(valid = true)
        }
    }

    override fun validateAll(fields: ModuleValidationFields): Boolean {
        val validName = name(fields.name).valid
        val validDescription = description(fields.description).valid
        val validTeacherFirstName = teacherFirstName(fields.teacherFirstName).valid
        val validTeacherLastName = teacherLastName(fields.teacherLastName).valid
        return validName && validDescription && validTeacherFirstName && validTeacherLastName
    }
}