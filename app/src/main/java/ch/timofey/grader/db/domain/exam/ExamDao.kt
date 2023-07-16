package ch.timofey.grader.db.domain.exam

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface ExamDao {
    @Insert
    suspend fun save(exam: Exam)

    @Delete
    suspend fun delete(exam: Exam)

    @Update
    suspend fun update(exam: Exam)

    @Query("SELECT * FROM exam")
    fun getAll(): Flow<List<Exam>>

    @Query("SELECT * FROM exam WHERE id LIKE :id")
    suspend fun getById(id: UUID): Exam?

    @Query("UPDATE exam SET is_selected = :value WHERE id LIKE :id")
    suspend fun updateIsSelected(id: UUID, value: Boolean)

    @Query("UPDATE exam SET on_delete = :value WHERE id LIKE :id")
    suspend fun updateOnDelete(id: UUID, value: Boolean)

    @Query("UPDATE module SET grade = :value WHERE id LIKE :id")
    suspend fun updateModuleGradeById(id: UUID, value: Double)
}