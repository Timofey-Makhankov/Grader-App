package ch.timofey.grader.ui.state

import ch.timofey.grader.db.domain.school.School

data class MainState(
    val schoolList: List<School> = emptyList()
)
