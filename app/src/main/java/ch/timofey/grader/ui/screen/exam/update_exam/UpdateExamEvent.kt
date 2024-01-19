package ch.timofey.grader.ui.screen.exam.update_exam

sealed class UpdateExamEvent {
    data object OnBackButtonPress : UpdateExamEvent()
    data object OnUpdateExamButtonPress : UpdateExamEvent()
    data class OnNameChange(val name: String) : UpdateExamEvent()
    data class OnDescriptionChange(val description: String) : UpdateExamEvent()
    data class OnGradeChange(val grade: String) : UpdateExamEvent()
    data class OnWeightChange(val weight: String) : UpdateExamEvent()
    data class OnSetDate(val date: Long) : UpdateExamEvent()
}