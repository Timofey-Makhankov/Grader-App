package ch.timofey.grader.ui.screen.division.update_division

sealed class UpdateDivisionEvent {
    data class OnNameChange(val name: String) : UpdateDivisionEvent()
    data class OnDescriptionChange(val description: String) : UpdateDivisionEvent()
    data class OnYearChange(val year: String) : UpdateDivisionEvent()
    data object OnBackButtonClick : UpdateDivisionEvent()
    data object OnUpdateDivision : UpdateDivisionEvent()
}