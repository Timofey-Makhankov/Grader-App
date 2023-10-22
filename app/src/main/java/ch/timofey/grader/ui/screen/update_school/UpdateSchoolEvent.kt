package ch.timofey.grader.ui.screen.update_school

import ch.timofey.grader.ui.screen.create_school.CreateSchoolEvent

sealed class UpdateSchoolEvent {
    object OnUpdateSchool : UpdateSchoolEvent()
    data class OnNameChange(val name: String) : UpdateSchoolEvent()
    data class OnDescriptionChange(val description: String) : UpdateSchoolEvent()
    data class OnAddressChange(val address: String) : UpdateSchoolEvent()
    data class OnZipChange(val zip: String) : UpdateSchoolEvent()
    data class OnCityChange(val city: String) : UpdateSchoolEvent()
    object OnReturnBack : UpdateSchoolEvent()
}