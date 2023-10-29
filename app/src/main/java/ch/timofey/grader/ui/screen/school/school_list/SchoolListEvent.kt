package ch.timofey.grader.ui.screen.school.school_list

import java.util.UUID

sealed class SchoolListEvent {
    object OnCreateSchool : SchoolListEvent()
    data class OnSwipeDelete(val id: UUID) : SchoolListEvent()
    data class OnDeleteItems(val route: String) : SchoolListEvent()
    data class OnCheckChange(val schoolId: UUID, val value: Boolean) : SchoolListEvent()
    data class OnItemClickDelete(val schoolId: UUID) : SchoolListEvent()
    data class OnUndoDeleteClick(val schoolId: UUID) : SchoolListEvent()
}
