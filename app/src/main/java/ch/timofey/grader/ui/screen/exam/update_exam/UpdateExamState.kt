package ch.timofey.grader.ui.screen.exam.update_exam

import ch.timofey.grader.db.domain.exam.Exam
import ch.timofey.grader.db.domain.exam.ExamValidationFields

data class UpdateExamState(
    val currentExam: Exam? = null,
    override val name: String = "",
    override val description: String = "",
    override val grade: String = "",
    override val weight: String = "",
    override val dateTaken: String = "",
    val validName: Boolean = true,
    val validDescription: Boolean = true,
    val validGrade: Boolean = true,
    val validWeight: Boolean = true,
    val validDate: Boolean = true,
    val errorMessageName: String = "",
    val errorMessageDescription: String = "",
    val errorMessageGrade: String = "",
    val errorMessageWeight: String = "",
    val errorMessageDate: String = "",
) : ExamValidationFields
