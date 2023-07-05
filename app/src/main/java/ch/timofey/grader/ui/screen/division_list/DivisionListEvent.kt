package ch.timofey.grader.ui.screen.division_list

import ch.timofey.grader.db.domain.division.Division
import java.util.UUID

sealed class DivisionListEvent {
    object OnReturnBack : DivisionListEvent()
    object OnCreateDivision : DivisionListEvent()
    data class OnCheckChange(val id: UUID, val value: Boolean) : DivisionListEvent()
    data class OnSwipeDelete(val division: Division) : DivisionListEvent()
}
