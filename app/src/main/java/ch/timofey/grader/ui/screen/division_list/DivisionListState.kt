package ch.timofey.grader.ui.screen.division_list

import ch.timofey.grader.db.domain.division.Division

data class DivisionListState(
    val divisionList: List<Division> = emptyList()
)
