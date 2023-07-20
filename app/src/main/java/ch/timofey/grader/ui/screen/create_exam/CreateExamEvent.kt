package ch.timofey.grader.ui.screen.create_exam

sealed class CreateExamEvent {
    object OnBackButtonPress : CreateExamEvent()
    object OnCreateExamButtonPress : CreateExamEvent()
    data class OnNameChange(val name: String) : CreateExamEvent()
    data class OnDescriptionChange(val description: String) : CreateExamEvent()
    data class OnGradeChange(val grade: String) : CreateExamEvent()
    data class OnWeightChange(val weight: String) : CreateExamEvent()
    data class OnDateChange(val date: String) : CreateExamEvent()
    data class OnSetDate(val date: Long) : CreateExamEvent()
}
