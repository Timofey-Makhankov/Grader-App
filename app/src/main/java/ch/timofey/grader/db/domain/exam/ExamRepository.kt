package ch.timofey.grader.db.domain.exam

import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface ExamRepository {
    suspend fun insertExam(exam: Exam)

    suspend fun deleteExam(exam: Exam)

    suspend fun getExamById(id: UUID): Exam?

    fun getAllExams(): Flow<List<Exam>>
}