package ch.timofey.grader.ui.screen.exam.create_exam

data class CreateExamState(
    val name: String = "",
    val description: String = "",
    val grade: String = "",
    val weight: String = "1.0",
    val date: String = "",
    val validName: Boolean = true,
    val validGrade: Boolean = true,
    val validWeight: Boolean = true,
    val validDate: Boolean = true,
    val errorMessageName: String = "",
    val errorMessageGrade: String = "",
    val errorMessageWeight: String = "",
    val errorMessageDate: String = "",
)
