package ch.timofey.grader.validation

import ch.timofey.grader.ui.screen.school.create_school.CreateSchoolState

object ValidateSchool {
    class Name(private val name: String) : Validatable {
        override fun validate(): ValidationResponse {
            return if (name.isNotBlank()) {
                if (name.length <= 60) {
                    ValidationResponse(isValid = true)
                } else {
                    ValidationResponse(
                        isValid = false,
                        errorMessage = "The Name has to be not longer than 60 Characters"
                    )
                }
            } else {
                ValidationResponse(isValid = false, errorMessage = "Name is Required")
            }
        }
    }

    class Address(private val address: String) : Validatable {
        override fun validate(): ValidationResponse {
            return if (address.isNotBlank()) {
                ValidationResponse(isValid = true)
            } else {
                ValidationResponse(isValid = false, errorMessage = "Address is Required")
            }
        }
    }

    class Description(private val description: String) : Validatable {
        override fun validate(): ValidationResponse {
            return ValidationResponse(isValid = true)
        }
    }

    class Zip(private val zip: String) : Validatable {
        override fun validate(): ValidationResponse {
            return if (zip.isNotBlank()) {
                ValidationResponse(isValid = true)
            } else {
                ValidationResponse(isValid = false, errorMessage = "Zip Code is Required")
            }
        }
    }

    class City(private val city: String) : Validatable {
        override fun validate(): ValidationResponse {
            return if (city.isNotBlank()) {
                ValidationResponse(isValid = true)
            } else {
                ValidationResponse(isValid = false, errorMessage = "City is Required")
            }
        }
    }

    fun validateAll(school: CreateSchoolState): Boolean {
        val validName = Name(school.name).validate().isValid
        val validAddress = Address(school.address).validate().isValid
        val validDescription = Description(school.description).validate().isValid
        val validZip = Zip(school.zip).validate().isValid
        val validCity = City(school.city).validate().isValid
        return validName && validAddress && validDescription && validZip && validCity
    }
}