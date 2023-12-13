package ch.timofey.grader.db.domain.school

import ch.timofey.grader.validation.Validatable
import ch.timofey.grader.validation.ValidationResponse

object SchoolValidation: Validatable<SchoolValidationFields> {
    fun name(name: String): ValidationResponse {
        return when {
            name.isBlank() -> ValidationResponse(valid = false, message = "Name is Required")
            name.length > 60 -> ValidationResponse(
                valid = false, message = "The Name has to be not longer than 60 Characters"
            )

            else -> ValidationResponse(valid = true)
        }
    }


    fun address(address: String): ValidationResponse {
        return if (address.isNotBlank()) {
            ValidationResponse(valid = true)
        } else {
            ValidationResponse(valid = false, message = "Address is Required")
        }
    }


    fun description(description: String): ValidationResponse {
        return ValidationResponse(valid = true)
    }


    fun zip(zip: String): ValidationResponse {
        return if (zip.isNotBlank()) {
            ValidationResponse(valid = true)
        } else {
            ValidationResponse(valid = false, message = "Zip Code is Required")
        }
    }

    fun city(city: String): ValidationResponse {
        return if (city.isNotBlank()) {
            ValidationResponse(valid = true)
        } else {
            ValidationResponse(valid = false, message = "City is Required")
        }
    }

    override fun validateAll(fields: SchoolValidationFields): Boolean {
        val validName = name(fields.name).valid
        val validAddress = address(fields.address).valid
        val validDescription = description(fields.description).valid
        val validZip = zip(fields.zip).valid
        val validCity = city(fields.city).valid
        return validName && validAddress && validDescription && validZip && validCity
    }
}