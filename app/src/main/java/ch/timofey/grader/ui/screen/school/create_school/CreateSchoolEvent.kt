package ch.timofey.grader.ui.screen.school.create_school

sealed class CreateSchoolEvent {
    data object OnCreateSchool : CreateSchoolEvent()
    data class OnNameChange(val name: String) : CreateSchoolEvent()
    data class OnDescriptionChange(val description: String) : CreateSchoolEvent()
    data class OnAddressChange(val address: String) : CreateSchoolEvent()
    data class OnZipChange(val zip: String) : CreateSchoolEvent()
    data class OnCityChange(val city: String) : CreateSchoolEvent()
    data object OnReturnBack : CreateSchoolEvent()
}