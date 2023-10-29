package ch.timofey.grader.ui.screen.division.create_division

sealed class CreateDivisionEvent {
    data class OnNameChange(val name: String) : CreateDivisionEvent()
    data class OnDescriptionChange(val description: String) : CreateDivisionEvent()
    data class OnYearChange(val year: String) : CreateDivisionEvent()
    object OnBackButtonClick : CreateDivisionEvent()
    object OnCreateDivision : CreateDivisionEvent()
}
