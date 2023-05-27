package ch.timofey.grader.ui.screen.school_list

import ch.timofey.grader.db.domain.school.School

data class SchoolListState(
    val schoolList: List<School> = emptyList()
)
