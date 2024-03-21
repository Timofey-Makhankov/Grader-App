package ch.timofey.grader.db.domain.exam

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.util.UUID

/**
 * The Dao Interface for the Exam Entity
 * @see Exam
 */
@Dao
interface ExamDao {
    /**
     * save exam entity
     *
     * @param exam entity
     * @see Exam
     */
    @Insert(entity = Exam::class, onConflict = OnConflictStrategy.IGNORE)
    suspend fun save(exam: Exam)

    /**
     * delete exam entity by id
     *
     * @param id [UUID]
     * @see Exam
     */
    @Query("DELETE FROM exam WHERE id = :id")
    suspend fun deleteById(id: UUID)

    /**
     * update exam entity
     *
     * @param exam entity
     * @see Exam
     */
    @Update(entity = Exam::class)
    suspend fun update(exam: Exam)

    /**
     * get flow list of exams
     *
     * @return flow list exams or empty flow list
     * @see Exam
     */
    @Query("SELECT * FROM exam")
    fun getAllFlow(): Flow<List<Exam>>

    /**
     * get list of exams
     *
     * @return list of exams or empty list
     * @see Exam
     */
    @Query("SELECT * FROM exam")
    fun getAll(): List<Exam>

    /**
     * get flow list of exams by parent module id
     *
     * @param id module [UUID]
     * @return flow list of exams from module id or empty flow list
     * @see Exam
     */
    @Query("SELECT * FROM exam WHERE module_id LIKE :id")
    fun getAllExamsFromModuleId(id: UUID): Flow<List<Exam>>

    /**
     * get exam entity by id
     *
     * @param id [UUID]
     * @return Optional Entity Exam
     * @see Exam
     */
    @Query("SELECT * FROM exam WHERE id LIKE :id")
    suspend fun getById(id: UUID): Exam?

    /**
     * update isSelected on Exam Entity by id and given value
     *
     * @param id [UUID]
     * @param value boolean
     * @see Exam
     */
    @Query("UPDATE exam SET is_selected = :value WHERE id LIKE :id")
    suspend fun updateIsSelected(id: UUID, value: Boolean)

    /**
     * update onDelete on exam Entity by id and given value
     *
     * @param id [UUID]
     * @param value boolean
     * @see Exam
     */
    @Query("UPDATE exam SET on_delete = :value WHERE id LIKE :id")
    suspend fun updateOnDelete(id: UUID, value: Boolean)

    /**
     * Update the average parent module grade to the module table
     *
     * @param id module [UUID]
     * @param value Double
     * @see Exam
     */
    @Query("UPDATE module SET grade = :value WHERE id LIKE :id")
    suspend fun updateModuleGradeById(id: UUID, value: Double)
}