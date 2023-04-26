package ch.timofey.grader.db.domain.module

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import ch.timofey.grader.db.domain.relations.ModuleWithExams
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface ModuleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(module: Module)
    @Delete
    suspend fun delete(module: Module)
    @Query("SELECT * FROM module")
    fun getAll(): Flow<List<Module>>
    @Query("SELECT * FROM module WHERE id LIKE :id")
    suspend fun getById(id: UUID): Module?

    @Transaction
    @Query("SELECT * FROM module")
    fun getAllWithExams(): Flow<List<ModuleWithExams>>

    @Transaction
    @Query("SELECT * FROM module WHERE id LIKE :id")
    suspend fun getWithExamsById(id: UUID): ModuleWithExams?
}