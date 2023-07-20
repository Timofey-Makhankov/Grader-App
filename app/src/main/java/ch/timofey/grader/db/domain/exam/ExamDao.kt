package ch.timofey.grader.db.domain.exam

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface ExamDao {
    @Insert(entity = Exam::class)
    suspend fun save(exam: Exam)

    @Delete(entity = Exam::class)
    suspend fun delete(exam: Exam)

    @Update(entity = Exam::class)
    suspend fun update(exam: Exam)

    @Query("SELECT * FROM exam")
    fun getAll(): Flow<List<Exam>>

    @Query("SELECT * FROM exam WHERE module_id LIKE :id")
    fun getAllExamsFromModuleId(id: UUID): Flow<List<Exam>>

    @Query("SELECT * FROM exam WHERE id LIKE :id")
    suspend fun getById(id: UUID): Exam?

    @Query("UPDATE exam SET is_selected = :value WHERE id LIKE :id")
    suspend fun updateIsSelected(id: UUID, value: Boolean)

    @Query("UPDATE exam SET on_delete = :value WHERE id LIKE :id")
    suspend fun updateOnDelete(id: UUID, value: Boolean)

    @Query("UPDATE module SET grade = :value WHERE id LIKE :id")
    suspend fun updateModuleGradeById(id: UUID, value: Double)
}