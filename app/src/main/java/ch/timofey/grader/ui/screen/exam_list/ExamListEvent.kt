package ch.timofey.grader.ui.screen.exam_list

import ch.timofey.grader.db.domain.exam.Exam
import java.util.UUID

sealed class ExamListEvent{
    object OnBackButtonClick : ExamListEvent()
    object OnFABClick : ExamListEvent()
    data class OnCheckChange(val id: UUID, val value: Boolean) : ExamListEvent()
    data class OnSwipeDelete(val exam: Exam) : ExamListEvent()
}
