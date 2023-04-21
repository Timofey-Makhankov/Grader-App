package ch.timofey.grader.db.domain.school

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import ch.timofey.grader.db.domain.school.School
import java.util.UUID

@Dao
interface SchoolDao {
    @Query("SELECT * FROM school")
    fun getAll(): List<School>

    @Query("SELECT * FROM school WHERE id LIKE :id")
    fun getById(id: UUID): School

    @Insert
    fun insert(division: School)

    @Delete
    fun delete(division: School)
}