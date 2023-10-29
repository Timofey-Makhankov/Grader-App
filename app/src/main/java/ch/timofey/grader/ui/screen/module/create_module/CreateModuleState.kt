package ch.timofey.grader.ui.screen.module.create_module

data class CreateModuleState(
    val name: String = "",
    val description: String = "",
    val teacherFirstname: String = "",
    val teacherLastname: String = "",
    val validName: Boolean = true,
    val validTeacherFirstname: Boolean = true,
    val validTeacherLastname: Boolean = true,
    val errorMessageName: String = "",
    val errorMessageTeacherFirstname: String = "",
    val errorMessageTeacherLastname: String = ""
)
