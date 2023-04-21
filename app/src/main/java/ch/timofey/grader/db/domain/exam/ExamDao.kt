package ch.timofey.grader.db.domain.exam

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import java.util.UUID

@Dao
interface ExamDao {
    @Query("SELECT * FROM exam")
    fun getAll(): List<Exam>

    @Query("SELECT * FROM exam WHERE id LIKE :id")
    fun getById(id: UUID): Exam

    @Insert
    fun insert(division: Exam)

    @Delete
    fun delete(division: Exam)
}