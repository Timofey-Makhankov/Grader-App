package ch.timofey.grader.ui.screen.division.division_list

import ch.timofey.grader.db.domain.division.Division

data class DivisionListState(
    val divisionList: List<Division> = emptyList(),
    val locationTitles: List<String> = emptyList(),
    val averageGrade: String = "",
    val averageGradeIsZero: Boolean? = null,
    val swipingEnabled: Boolean = false,
    val showPoints: Boolean = false,
    val showNavigationIcons: Boolean = false,
    val swapNavigation: Boolean = false,
    val colorGrades: Boolean = true
)
