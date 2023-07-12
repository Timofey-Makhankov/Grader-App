package ch.timofey.grader.ui.screen.module_list

import ch.timofey.grader.db.domain.module.Module

data class ModuleListState(
    val moduleList: List<Module> = emptyList(),
    val averageGrade: String = "",
    val averageGradeIsZero: Boolean? = null
)
