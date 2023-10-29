package ch.timofey.grader.ui.screen.exam.exam_list

import ch.timofey.grader.db.domain.exam.Exam

data class ExamListState(
    val exams: List<Exam> = emptyList(),
    val locationsTitles: List<String> = emptyList(),
    val averageGrade: String = "",
    val averageGradeIsZero: Boolean? = null,
    val showPoints: Boolean = false
)
