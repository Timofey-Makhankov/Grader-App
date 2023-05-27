package ch.timofey.grader.ui.screen.create_school

sealed class CreateSchoolEvent {
    object OnCreateSchool : CreateSchoolEvent()
    data class OnNameChange(val name: String) : CreateSchoolEvent()
    data class OnDescriptionChange(val description: String) : CreateSchoolEvent()
    data class OnAddressChange(val address: String) : CreateSchoolEvent()
    data class OnZipChange(val zip: String) : CreateSchoolEvent()
    data class OnCityChange(val city: String) : CreateSchoolEvent()
    object OnReturnBack : CreateSchoolEvent()
}