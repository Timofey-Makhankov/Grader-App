package ch.timofey.grader.ui.screen.school_list

import ch.timofey.grader.db.domain.school.School
import java.util.UUID

sealed class SchoolListEvent {
    object OnCreateSchool : SchoolListEvent()
    data class OnCheckChange(val id: UUID, val value: Boolean) : SchoolListEvent()
    data class OnSwipeDelete(val school: School) : SchoolListEvent()
    object OnUndoDeleteClick : SchoolListEvent()
}
