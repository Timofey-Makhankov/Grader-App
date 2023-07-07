package ch.timofey.grader.ui.screen.create_exam

data class CreateExamState(
    val name: String = "",
    val description: String = "",
    val grade: String = "",
    val weight: String = "",
    val date: String = "",
)
