package ch.timofey.grader.ui.screen.create_module

data class CreateModuleState(
    val name: String = "",
    val description : String = "",
    val teacherFirstname: String = "",
    val teacherLastname: String = "",
    val validName: Boolean = true,
    val errorMessageName: String = ""
)
