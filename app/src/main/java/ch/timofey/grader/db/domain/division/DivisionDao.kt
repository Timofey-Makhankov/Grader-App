package ch.timofey.grader.db.domain.division

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import ch.timofey.grader.db.domain.division.Division
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface DivisionDao {
    @Query("SELECT * FROM division")
    fun getAll(): Flow<List<Division>>

    @Query("SELECT * FROM division WHERE id LIKE :id")
    suspend fun getById(id: UUID): Division?

    @Insert
    suspend fun insert(division: Division)

    @Delete
    suspend fun delete(division: Division)
}