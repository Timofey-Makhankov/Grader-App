package ch.timofey.grader.db.domain.module

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import ch.timofey.grader.db.domain.module.Module
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface ModuleDao {
    @Query("SELECT * FROM module")
    fun getAll(): Flow<List<Module>>

    @Query("SELECT * FROM module WHERE id LIKE :id")
    suspend fun getById(id: UUID): Module?

    @Insert
    suspend fun insert(module: Module)

    @Delete
    suspend fun delete(module: Module)
}