package ch.timofey.grader.ui.screen.exam.create_exam

sealed class CreateExamEvent {
    data object OnBackButtonPress : CreateExamEvent()
    data object OnCreateExamButtonPress : CreateExamEvent()
    data class OnNameChange(val name: String) : CreateExamEvent()
    data class OnDescriptionChange(val description: String) : CreateExamEvent()
    data class OnGradeChange(val grade: String) : CreateExamEvent()
    data class OnWeightChange(val weight: String) : CreateExamEvent()
    data class OnSetDate(val date: Long) : CreateExamEvent()
}
