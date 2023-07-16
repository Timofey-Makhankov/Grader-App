package ch.timofey.grader.db.domain.exam

import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface ExamRepository {
    suspend fun saveExam(exam: Exam)
    suspend fun deleteExam(exam: Exam)
    suspend fun updateExam(exam: Exam)
    suspend fun getExamById(id: UUID): Exam?
    suspend fun updateIsSelectedExam(id: UUID, value: Boolean)
    suspend fun updateOnDelete(id: UUID, value: Boolean)
    suspend fun updateModuleGradeById(id: UUID, value: Double)
    fun getAllExams(): Flow<List<Exam>>
}