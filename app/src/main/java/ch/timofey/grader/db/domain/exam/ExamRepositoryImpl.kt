package ch.timofey.grader.db.domain.exam

import kotlinx.coroutines.flow.Flow
import java.util.UUID

class ExamRepositoryImpl(private val examDao: ExamDao): ExamRepository {
    override suspend fun insertExam(exam: Exam) {
        examDao.insert(exam)
    }

    override suspend fun deleteExam(exam: Exam) {
        examDao.delete(exam)
    }


    override suspend fun getExamById(id: UUID): Exam? {
        return examDao.getById(id)
    }


    override fun getAllExams(): Flow<List<Exam>> {
        return examDao.getAll()
    }
}