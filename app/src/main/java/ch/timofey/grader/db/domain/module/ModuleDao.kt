package ch.timofey.grader.db.domain.module

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import ch.timofey.grader.db.domain.relations.ModuleWithExams
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface ModuleDao {
    @Insert
    suspend fun save(module: Module)

    @Delete
    suspend fun delete(module: Module)

    @Update
    suspend fun update(module: Module)

    @Query("SELECT * FROM module")
    fun getAll(): Flow<List<Module>>

    @Query("SELECT * FROM module WHERE id LIKE :id")
    suspend fun getById(id: UUID): Module?

    @Query("UPDATE module SET is_selected = :value WHERE id LIKE :id")
    suspend fun updateIsSelected(id: UUID, value: Boolean)

    @Query("UPDATE module SET on_delete = :value WHERE id LIKE :id")
    suspend fun updateOnDelete(id: UUID, value: Boolean)

    @Query("UPDATE division SET grade = :value WHERE id LIKE :id")
    suspend fun updateDivisionGradeById(id: UUID, value: Double)

    @Transaction
    @Query("SELECT * FROM module")
    fun getAllWithExams(): Flow<List<ModuleWithExams>>

    @Transaction
    @Query("SELECT * FROM module WHERE id LIKE :id")
    suspend fun getWithExamsById(id: UUID): ModuleWithExams?
}