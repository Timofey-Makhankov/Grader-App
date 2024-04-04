package ch.timofey.grader.ui.screen.module.module_list

import ch.timofey.grader.db.domain.module.Module

data class ModuleListState(
    val moduleList: List<Module> = emptyList(),
    val locationsTitles: List<String> = emptyList(),
    val averageGrade: String = "",
    val averageGradeIsZero: Boolean? = null,
    val swipingEnabled: Boolean? = null,
    val showPoints: Boolean? = null,
    val showNavigationIcons: Boolean? = null,
)
