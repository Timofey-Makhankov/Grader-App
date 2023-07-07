package ch.timofey.grader.ui.screen.exam_list

import ch.timofey.grader.db.domain.exam.Exam

data class ExamListState(
    val exams: List<Exam> = emptyList()
)
