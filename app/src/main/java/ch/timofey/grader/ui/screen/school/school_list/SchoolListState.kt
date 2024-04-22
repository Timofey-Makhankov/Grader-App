package ch.timofey.grader.ui.screen.school.school_list

import ch.timofey.grader.db.domain.school.School

data class SchoolListState(
    val schoolList: List<School> = emptyList(),
    val averageGrade: String = "",
    val showNavigationIcons: Boolean? = null,
    val averageGradeIsZero: Boolean? = null,
    val swipingEnabled: Boolean = false,
    val showPoints: Boolean = false,
    val minimumGrade: Double? = null,
    val colorGrades: Boolean = true,
    val swapNavigation: Boolean = true
)
