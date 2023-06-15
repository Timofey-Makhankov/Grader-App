package ch.timofey.grader.ui.screen.create_division

import java.time.Year

sealed class CreateDivisionEvent {
    data class OnNameChange(val name: String) : CreateDivisionEvent()
    data class OnDescriptionChange(val description: String) : CreateDivisionEvent()
    data class OnYearChange(val year: String) : CreateDivisionEvent()
    object OnBackButtonClick : CreateDivisionEvent()
    object OnCreateDivision : CreateDivisionEvent()
}
