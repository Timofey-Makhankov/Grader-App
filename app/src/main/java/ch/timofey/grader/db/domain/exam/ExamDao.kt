package ch.timofey.grader.db.domain.exam

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface ExamDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(exam: Exam)

    @Delete
    suspend fun delete(exam: Exam)

    @Query("UPDATE exam SET is_selected = :value WHERE :id LIKE id")
    suspend fun updateIsSelected(id: UUID, value: Boolean)

    @Query("SELECT * FROM exam")
    fun getAll(): Flow<List<Exam>>

    @Query("SELECT * FROM exam WHERE id LIKE :id")
    suspend fun getById(id: UUID): Exam?
}