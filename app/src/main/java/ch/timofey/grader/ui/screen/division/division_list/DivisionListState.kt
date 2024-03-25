package ch.timofey.grader.ui.screen.division.division_list

import ch.timofey.grader.db.domain.division.Division

data class DivisionListState(
    val divisionList: List<Division> = emptyList(),
    val locationTitles: List<String> = emptyList(),
    val averageGrade: String = "",
    val averageGradeIsZero: Boolean? = null,
    val swipingEnabled: Boolean? = null,
    val showPoints: Boolean? = null,
)
