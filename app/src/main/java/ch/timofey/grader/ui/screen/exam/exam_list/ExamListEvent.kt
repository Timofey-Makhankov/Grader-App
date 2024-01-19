package ch.timofey.grader.ui.screen.exam.exam_list

import ch.timofey.grader.db.domain.exam.Exam
import java.util.UUID

sealed class ExamListEvent {
    data object OnBackButtonClick : ExamListEvent()
    data object OnFABClick : ExamListEvent()
    data class OnDeleteItems(val route: String) : ExamListEvent()
    data class OnCheckChange(val id: UUID, val value: Boolean) : ExamListEvent()
    data class OnSwipeDelete(val id: UUID) : ExamListEvent()
    data class OnUndoDeleteClick(val id: UUID) : ExamListEvent()
    data class OnDeleteButtonClick(val examId: UUID) : ExamListEvent()
}
