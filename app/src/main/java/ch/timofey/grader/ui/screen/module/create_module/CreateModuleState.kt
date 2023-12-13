package ch.timofey.grader.ui.screen.module.create_module

import ch.timofey.grader.db.domain.module.ModuleValidationFields

data class CreateModuleState(
    override val name: String = "",
    override val description: String = "",
    override val teacherFirstName: String = "",
    override val teacherLastName: String = "",
    val validName: Boolean = true,
    val validTeacherFirstname: Boolean = true,
    val validTeacherLastname: Boolean = true,
    val validDescription: Boolean = true,
    val errorMessageName: String = "",
    val errorMessageTeacherFirstname: String = "",
    val errorMessageTeacherLastname: String = "",
    val errorMessageDescription: String = ""
) : ModuleValidationFields
