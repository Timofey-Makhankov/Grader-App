package ch.timofey.grader.ui.screen.school_list

import java.util.UUID

sealed class SchoolListEvent {
    object OnCreateSchool : SchoolListEvent()
    data class OnCheckChange(val id: UUID, val value: Boolean) : SchoolListEvent()
}
