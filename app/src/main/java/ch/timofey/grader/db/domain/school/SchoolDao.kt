package ch.timofey.grader.db.domain.school

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import ch.timofey.grader.db.domain.relations.SchoolWithDivisions
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface SchoolDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(school: School)

    @Delete
    suspend fun delete(school: School)
    @Query("SELECT * FROM school")
    fun getAll(): Flow<List<School>>

    @Query("SELECT * FROM school WHERE id LIKE :id")
    suspend fun getById(id: UUID): School?

    @Transaction
    @Query("SELECT * FROM school")
    fun getAllWithDivisions(): Flow<List<SchoolWithDivisions>>

    @Transaction
    @Query("SELECT * FROM school WHERE id LIKE :id")
    suspend fun getWithExamsById(id: UUID): SchoolWithDivisions?
}