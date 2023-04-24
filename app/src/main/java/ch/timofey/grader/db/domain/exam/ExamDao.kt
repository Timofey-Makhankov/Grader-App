package ch.timofey.grader.db.domain.exam

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface ExamDao {
    @Query("SELECT * FROM exam")
    fun getAll(): Flow<List<Exam>>

    @Query("SELECT * FROM exam WHERE id LIKE :id")
    fun getById(id: UUID): Exam?

    @Insert
    fun insert(exam: Exam)

    @Delete
    fun delete(exam: Exam)
}