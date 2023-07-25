package ch.timofey.grader.ui.screen.school_list

import ch.timofey.grader.db.domain.school.School
import java.util.UUID

sealed class SchoolListEvent {
    object OnCreateSchool : SchoolListEvent()
    data class OnDeleteItems(val route: String) : SchoolListEvent()
    data class OnCheckChange(val id: UUID, val value: Boolean) : SchoolListEvent()
    data class OnSwipeDelete(val id: UUID) : SchoolListEvent()
    data class OnUndoDeleteClick(val id: UUID) : SchoolListEvent()
}
