package ch.timofey.grader.ui.screen.school.create_school

import ch.timofey.grader.validation.Validatable

sealed class CreateSchoolEvent {
    data object OnCreateSchool : CreateSchoolEvent()
    data class OnNameChange(val name: String, val validator: Validatable) : CreateSchoolEvent()
    data class OnDescriptionChange(val description: String, val validator: Validatable) : CreateSchoolEvent()
    data class OnAddressChange(val address: String, val validator: Validatable) : CreateSchoolEvent()
    data class OnZipChange(val zip: String, val validator: Validatable) : CreateSchoolEvent()
    data class OnCityChange(val city: String, val validator: Validatable) : CreateSchoolEvent()
    data object OnReturnBack : CreateSchoolEvent()
}