package ch.timofey.grader.db.domain.module

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import ch.timofey.grader.db.domain.module.Module
import java.util.UUID

@Dao
interface ModuleDao {
    @Query("SELECT * FROM module")
    fun getAll(): List<Module>

    @Query("SELECT * FROM module WHERE id LIKE :id")
    fun getById(id: UUID): Module

    @Insert
    fun insert(division: Module)

    @Delete
    fun delete(division: Module)
}