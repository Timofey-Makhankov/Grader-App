package ch.timofey.grader.db.domain.module

import androidx.room.*
import ch.timofey.grader.db.domain.relations.ModuleWithExams
import kotlinx.coroutines.flow.Flow
import java.util.UUID

/**
 * The Dao Interface for the Module Entity
 * @see Module
 */
@Dao
interface ModuleDao {
    /**
     * save given entity to the database
     *
     * @param module entity
     * @see Module
     */
    @Insert(entity = Module::class)
    suspend fun save(module: Module)

    /**
     * delete a module by its Id
     *
     * @param id [UUID]
     * @see Module
     */
    @Query("DELETE FROM module WHERE id = :id")
    suspend fun deleteById(id: UUID)

    /**
     * update a module entity
     *
     * @param module [Module]
     * @see Module
     */
    @Update(entity = Module::class)
    suspend fun update(module: Module)

    /**
     * Get a Flow of list of module entities
     *
     * @return a Flow List of Modules or empty flow list
     * @see Module
     */
    @Query("SELECT * FROM module")
    fun getAllFlow(): Flow<List<Module>>

    /**
     * Get a list of module entities
     *
     * @return a Flow List of Modules or empty list
     * @see Module
     */
    @Query("SELECT * FROM module")
    fun getAll(): List<Module>

    /**
     * get a module entity by Id
     *
     * @param id [UUID]
     * @return an Optional Module Entity
     * @see Module
     */
    @Query("SELECT * FROM module WHERE id LIKE :id")
    suspend fun getById(id: UUID): Module?

    /**
     * get a flow list of Module entities from given division id
     *
     * @param id division [UUID]
     * @return a flow list of modules or empty flow list
     * @see Module
     */
    @Query("SELECT * FROM module WHERE division_id LIKE :id")
    fun getAllModulesFromDivisionId(id: UUID): Flow<List<Module>>

    /**
     * update isSelected on module Entity by id and given value
     *
     * @param id [UUID]
     * @param value Boolean
     * @see Module
     */
    @Query("UPDATE module SET is_selected = :value WHERE id LIKE :id")
    suspend fun updateIsSelected(id: UUID, value: Boolean)

    /**
     * update onDelete on module Entity by id and given value
     *
     * @param id [UUID]
     * @param value Boolean
     * @see Module
     */
    @Query("UPDATE module SET on_delete = :value WHERE id LIKE :id")
    suspend fun updateOnDelete(id: UUID, value: Boolean)

    /**
     * Update the average school grade to the division table
     *
     * @param id [UUID]
     * @param value average grade
     * @see Module
     */
    @Query("UPDATE division SET grade = :value WHERE id LIKE :id")
    suspend fun updateDivisionGradeById(id: UUID, value: Double)

    /**
     * get flow list of divisions with exams
     *
     * @return Flow list of divisions with exams or an empty flow list
     * @see Module
     */
    @Transaction
    @Query("SELECT * FROM module")
    fun getAllWithExams(): Flow<List<ModuleWithExams>>

    /**
     * get a module with exams by id
     *
     * @param id [UUID]
     * @return Optional division Entity with modules
     * @see Module
     */
    @Transaction
    @Query("SELECT * FROM module WHERE id LIKE :id")
    suspend fun getWithExamsById(id: UUID): ModuleWithExams?
}