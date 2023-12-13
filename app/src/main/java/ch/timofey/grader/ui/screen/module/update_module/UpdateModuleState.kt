package ch.timofey.grader.ui.screen.module.update_module

import ch.timofey.grader.db.domain.module.Module
import ch.timofey.grader.db.domain.module.ModuleValidationFields

data class UpdateModuleState(
    val currentModule: Module? = null,
    override val name: String = "",
    override val description: String = "",
    override val teacherFirstName: String = "",
    override val teacherLastName: String = "",
    val validName: Boolean = true,
    val validDescription: Boolean = true,
    val validTeacherFirstName: Boolean = true,
    val validTeacherLastName: Boolean = true,
    val errorMessageName: String = "",
    val errorMessageDescription: String = "",
    val errorMessageTeacherFirstName: String = "",
    val errorMessageTeacherLastName: String = ""
) : ModuleValidationFields
