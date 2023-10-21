package ch.timofey.grader.db.domain.exam

import android.util.Log
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class ExamRepositoryImpl(private val examDao: ExamDao) : ExamRepository {
    private val tag: String = "ExamRepositoryImpl"
    override suspend fun saveExam(exam: Exam) {
        Log.v(tag, "trying to save exam")
        examDao.save(exam)
        Log.d(tag, "exam entity saved")
    }

    override suspend fun deleteExamById(id: UUID) {
        Log.v(tag, "trying to delete exam by id: $id")
        examDao.deleteById(id)
        Log.d(tag, "exam entity deleted")
    }

    override suspend fun updateExam(exam: Exam) {
        Log.v(tag, "trying to update exam")
        examDao.update(exam)
        Log.d(tag, "exam entity updated")
    }

    override suspend fun getExamById(id: UUID): Exam? {
        Log.v(tag, "trying to find exam entity by id: $id")
        val result = examDao.getById(id)
        Log.d(tag, "Optional Exam found")
        return result
    }

    override suspend fun updateIsSelectedExam(id: UUID, value: Boolean) {
        Log.v(tag, "trying to update isSelected on Exam by id: $id and value: $value")
        examDao.updateIsSelected(id, value)
        Log.d(tag, "isSelected on exam updated")
    }

    override suspend fun updateOnDelete(id: UUID, value: Boolean) {
        Log.v(tag, "trying to update onDelete on Exam by id: $id and value: $value")
        examDao.updateOnDelete(id, value)
        Log.d(tag, "onDelete on exam updated")
    }

    override suspend fun updateModuleGradeById(id: UUID, value: Double) {
        Log.v(tag, "trying to update average grade on parent module id: $id and grade: $value")
        examDao.updateModuleGradeById(id, value)
        Log.d(tag, "average grade on module updated")
    }

    override fun getAllExamsFlow(): Flow<List<Exam>> {
        Log.v(tag, "trying to get flow list of exams")
        val result = examDao.getAllFlow()
        Log.d(tag, "flow list exams found")
        return result
    }

    override fun getAllExams(): List<Exam> {
        Log.v(tag, "trying to get list of exams")
        val result = examDao.getAll()
        Log.d(tag, "list of exams found")
        return result
    }

    override fun getAllExamsFromModuleId(id: UUID): Flow<List<Exam>> {
        Log.v(tag, "trying to get flow list of exams from module id: $id")
        val result =  examDao.getAllExamsFromModuleId(id)
        Log.d(tag, "flow list exams from module found")
        return result
    }
}