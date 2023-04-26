package ch.timofey.grader.db.domain.division

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import ch.timofey.grader.db.domain.relations.DivisionWithModules
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface DivisionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(division: Division)

    @Delete
    suspend fun delete(division: Division)
    @Query("SELECT * FROM division")
    fun getAll(): Flow<List<Division>>

    @Query("SELECT * FROM division WHERE id LIKE :id")
    suspend fun getById(id: UUID): Division?

    @Transaction
    @Query("SELECT * FROM division")
    fun getAllWithModules(): Flow<List<DivisionWithModules>>

    @Transaction
    @Query("SELECT * FROM division WHERE id LIKE :id")
    suspend fun getWithModulesById(id: UUID): DivisionWithModules?
}