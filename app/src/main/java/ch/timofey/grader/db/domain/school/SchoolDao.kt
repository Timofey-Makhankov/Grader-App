package ch.timofey.grader.db.domain.school

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import ch.timofey.grader.db.domain.school.School
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface SchoolDao {
    @Query("SELECT * FROM school")
    fun getAll(): Flow<List<School>>

    @Query("SELECT * FROM school WHERE id LIKE :id")
    fun getById(id: UUID): School?

    @Insert
    fun insert(school: School)

    @Delete
    fun delete(school: School)
}