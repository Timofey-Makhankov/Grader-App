package ch.timofey.grader.db.domain.division

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import ch.timofey.grader.db.domain.relations.DivisionWithModules
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface DivisionDao {
    @Insert
    suspend fun save(division: Division)

    @Delete
    suspend fun delete(division: Division)

    @Update
    suspend fun update(division: Division)

    @Query("SELECT * FROM division")
    fun getAll(): Flow<List<Division>>

    @Query("SELECT * FROM division WHERE id LIKE :id")
    suspend fun getById(id: UUID): Division?

    @Query("UPDATE division SET is_selected = :value WHERE id LIKE :id")
    suspend fun updateIsSelected(id: UUID, value: Boolean)

    @Query("UPDATE division SET on_delete = :value WHERE id LIKE :id")
    suspend fun updateOnDelete(id: UUID, value: Boolean)

    @Query("UPDATE school SET grade = :value WHERE id LIKE :id")
    suspend fun updateSchoolGradeById(id: UUID, value: Double)

    @Transaction
    @Query("SELECT * FROM division")
    fun getAllWithModules(): Flow<List<DivisionWithModules>>

    @Transaction
    @Query("SELECT * FROM division WHERE id LIKE :id")
    suspend fun getAllWithModulesById(id: UUID): DivisionWithModules?

    @Transaction
    @Query("SELECT * FROM division WHERE school_id LIKE :id")
    fun getAllDivisionsFromSchoolId(id: UUID): Flow<List<Division>>
}