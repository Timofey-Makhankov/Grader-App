package ch.timofey.grader.validation

object ValidateSchool {
    fun name(name: String): ValidationResponse {
        return when {
            name.isBlank() -> ValidationResponse(isValid = false, errorMessage = "Name is Required")
            name.length > 60 -> ValidationResponse(
                isValid = false, errorMessage = "The Name has to be not longer than 60 Characters"
            )

            else -> ValidationResponse(isValid = true)
        }
    }


    fun address(address: String): ValidationResponse {
        return if (address.isNotBlank()) {
            ValidationResponse(isValid = true)
        } else {
            ValidationResponse(isValid = false, errorMessage = "Address is Required")
        }
    }


    fun description(description: String): ValidationResponse {
        return ValidationResponse(isValid = true)
    }


    fun zip(zip: String): ValidationResponse {
        return if (zip.isNotBlank()) {
            ValidationResponse(isValid = true)
        } else {
            ValidationResponse(isValid = false, errorMessage = "Zip Code is Required")
        }
    }

    fun city(city: String): ValidationResponse {
        return if (city.isNotBlank()) {
            ValidationResponse(isValid = true)
        } else {
            ValidationResponse(isValid = false, errorMessage = "City is Required")
        }
    }

    fun validateAll(school: SchoolValidationFields): Boolean {
        val validName = name(school.name).isValid
        val validAddress = address(school.address).isValid
        val validDescription = description(school.description).isValid
        val validZip = zip(school.zip).isValid
        val validCity = city(school.city).isValid
        return validName && validAddress && validDescription && validZip && validCity
    }
}