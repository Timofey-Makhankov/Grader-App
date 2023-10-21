package ch.timofey.grader.db.domain.exam

import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface ExamRepository {
    /**
     * save Exam Entity
     *
     * @param exam entity
     * @see Exam
     */
    suspend fun saveExam(exam: Exam)

    /**
     * delete exam entity by id
     *
     * @param id [UUID]
     * @see Exam
     */
    suspend fun deleteExamById(id: UUID)

    /**
     * update Exam entity
     *
     * @param exam
     * @see Exam
     */
    suspend fun updateExam(exam: Exam)

    /**
     * get Exam entity by id
     *
     * @param id [UUID]
     * @return Optional Exam Entity
     */
    suspend fun getExamById(id: UUID): Exam?

    /**
     * update isSelected field in exam entity by id
     *
     * @param id [UUID]
     * @param value boolean
     * @see Exam
     */
    suspend fun updateIsSelectedExam(id: UUID, value: Boolean)

    /**
     * update onDelete field in division entity by id
     *
     * @param id [UUID]
     * @param value boolean
     * @see Exam
     */
    suspend fun updateOnDelete(id: UUID, value: Boolean)

    /**
     * update average grade for parent module entity by id
     *
     * @param id module [UUID]
     * @param value Double
     * @see Exam
     */
    suspend fun updateModuleGradeById(id: UUID, value: Double)

    /**
     * get a flow list of exams
     *
     * @return flow list of exams or empty flow list
     * @see Exam
     */
    fun getAllExamsFlow(): Flow<List<Exam>>

    /**
     * get list of exams
     *
     * @return list of exams or empty list
     * @see Exam
     */
    fun getAllExams(): List<Exam>

    /**
     * get flow list of exams by parent module id
     *
     * @param id module [UUID]
     * @return flow list of exams or empty flow list
     * @see Exam
     */
    fun getAllExamsFromModuleId(id: UUID): Flow<List<Exam>>
}