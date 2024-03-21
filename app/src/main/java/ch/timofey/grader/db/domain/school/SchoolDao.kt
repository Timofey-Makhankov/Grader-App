package ch.timofey.grader.db.domain.school

import androidx.room.*
import ch.timofey.grader.db.domain.relations.SchoolWithDivisions
import kotlinx.coroutines.flow.Flow
import java.util.UUID

/**
 * The Dao Interface for the School Entity
 * @see School
 */
@Dao
interface SchoolDao {
    /**
     * save a given Entity to the Room Database,
     * If unable to create, abort the operation
     *
     * @param school [School] Entity
     * @see School
     */
    @Insert(entity = School::class, onConflict = OnConflictStrategy.IGNORE)
    suspend fun save(school: School)

    /**
     * delete a given Entity in the Room Database
     *
     * @param id [UUID]
     * @see School
     */
    @Query("DELETE FROM school WHERE id = :id")
    suspend fun deleteById(id: UUID)

    /**
     * Update an Entity with the updated values from given School Entity,
     * If unable to update, abort the operation
     *
     * @param school [School] Entity
     * @see School
     */
    @Update(entity = School::class, onConflict = OnConflictStrategy.ABORT)
    suspend fun update(school: School)

    /**
     * Get a Flow List of all schools in the Room database
     *
     * @return Flow List of School Entity
     * @see School
     */
    @Query("SELECT * FROM school")
    fun getAllWithFlow(): Flow<List<School>>

    /**
     * Get a List of all schools in the Room database
     *
     * @return List of School Entity
     * @see School
     */
    @Query("SELECT * FROM school")
    fun getAll(): List<School>

    /**
     * Get a School Entity with the given Id,
     * if the entity is not found, it will return null
     *
     * @param id [UUID] of School Entity
     * @return School Entity, otherwise returns null when school entity not found
     * @see School
     */
    @Query("SELECT * FROM school WHERE id LIKE :id")
    suspend fun getById(id: UUID): School?

    /**
     * Update the OnSelect field in the School entity with a boolean value
     *
     * @param id [UUID] of School Entity
     * @param value boolean
     * @see School
     */
    @Transaction
    @Query("UPDATE school SET is_selected = :value WHERE id LIKE :id")
    fun updateIsSelected(id: UUID, value: Boolean)

    /**
     * Update the OnDelete field in the School entity with a boolean value
     *
     * @param id [UUID] of School Entity
     * @param value boolean
     * @see School
     */
    @Transaction
    @Query("UPDATE school SET on_delete = :value WHERE id LIKE :id")
    fun updateOnDelete(id: UUID, value: Boolean)

    /**
     * Get a Flow List of all schools with Divisions in the Room database
     *
     * @return Flow List of Schools with Divisions
     * @see School
     */
    @Transaction
    @Query("SELECT * FROM school")
    fun getAllWithDivisions(): Flow<List<SchoolWithDivisions>>

    /**
     * Get A school entity with Divisions with the given id,
     * if the entity is not found, return null
     *
     * @param id [UUID] of School entity
     * @return School Entity with Divisions, otherwise return null when school entity is not found
     * @see School
     */
    @Transaction
    @Query("SELECT * FROM school WHERE id LIKE :id")
    suspend fun getWithDivisionsById(id: UUID): SchoolWithDivisions?
}