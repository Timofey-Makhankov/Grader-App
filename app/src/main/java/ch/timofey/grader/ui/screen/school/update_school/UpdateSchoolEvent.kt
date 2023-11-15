package ch.timofey.grader.ui.screen.school.update_school

sealed class UpdateSchoolEvent {
    data object OnUpdateSchool : UpdateSchoolEvent()
    data class OnNameChange(val name: String) : UpdateSchoolEvent()
    data class OnDescriptionChange(val description: String) : UpdateSchoolEvent()
    data class OnAddressChange(val address: String) : UpdateSchoolEvent()
    data class OnZipChange(val zip: String) : UpdateSchoolEvent()
    data class OnCityChange(val city: String) : UpdateSchoolEvent()
    data object OnReturnBack : UpdateSchoolEvent()
}