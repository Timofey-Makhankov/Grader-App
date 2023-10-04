package ch.timofey.grader.db.domain.school

import androidx.room.*
import ch.timofey.grader.db.domain.relations.SchoolWithDivisions
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface SchoolDao {
    @Insert(entity = School::class)
    suspend fun save(school: School)

    @Delete(entity = School::class)
    suspend fun delete(school: School)

    @Update(entity = School::class, onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(school: School)

    @Query("SELECT * FROM school")
    fun getAll(): Flow<List<School>>

    @Query("SELECT * FROM school WHERE id LIKE :id")
    suspend fun getById(id: UUID): School?

    @Transaction
    @Query("UPDATE school SET is_selected = :value WHERE id LIKE :id")
    fun updateIsSelected(id: UUID, value: Boolean)

    @Transaction
    @Query("UPDATE school SET on_delete = :value WHERE id LIKE :id")
    fun updateOnDelete(id: UUID, value: Boolean)

    @Transaction
    @Query("SELECT * FROM school")
    fun getAllWithDivisions(): Flow<List<SchoolWithDivisions>>

    @Transaction
    @Query("SELECT * FROM school WHERE id LIKE :id")
    suspend fun getWithDivisionsById(id: UUID): SchoolWithDivisions?
}