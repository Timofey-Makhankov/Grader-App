package ch.timofey.grader.db.domain.division

import androidx.room.*
import ch.timofey.grader.db.domain.relations.DivisionWithModules
import kotlinx.coroutines.flow.Flow
import java.util.UUID

/**
 * The Dao Interface for the Division Entity
 * @see Division
 */
@Dao
interface DivisionDao {
    /**
     * save division entity
     *
     * @param division entity
     * @see Division
     */
    @Insert(entity = Division::class)
    suspend fun save(division: Division)

    /**
     * delete a division entity by Id
     *
     * @param id [UUID]
     * @see Division
     */
    @Query("DELETE FROM division WHERE id LIKE :id")
    suspend fun deleteById(id: UUID)

    /**
     * update a division entity
     *
     * @param division entity
     * @see Entity
     */
    @Update(entity = Division::class)
    suspend fun update(division: Division)

    /**
     * get flow of all divisions
     *
     * @return flow list of division or empty list when nothing has been found
     * @see Division
     */
    @Query("SELECT * FROM division")
    fun getAllFlow(): Flow<List<Division>>

    /**
     * get all divisions
     *
     * @return list of division or empty list when nothing has been found
     * @see Division
     */
    @Query("SELECT * FROM division")
    fun getAll(): List<Division>

    /**
     * Get Division entity by Id
     *
     * @param id [UUID]
     * @return Optional Division
     * @see Division
     */
    @Query("SELECT * FROM division WHERE id LIKE :id")
    suspend fun getById(id: UUID): Division?

    /**
     * update isSelected on division Entity by id and given value
     *
     * @param id [UUID]
     * @param value Boolean
     * @see Division
     */
    @Query("UPDATE division SET is_selected = :value WHERE id LIKE :id")
    suspend fun updateIsSelected(id: UUID, value: Boolean)

    /**
     * update onDelete on division Entity by id and given value
     *
     * @param id [UUID]
     * @param value Boolean
     * @see Division
     */
    @Query("UPDATE division SET on_delete = :value WHERE id LIKE :id")
    suspend fun updateOnDelete(id: UUID, value: Boolean)

    /**
     * Update the average school grade to the school table
     *
     * @param id [UUID]
     * @param value average grade
     * @see Division
     */
    @Query("UPDATE school SET grade = :value WHERE id LIKE :id")
    suspend fun updateSchoolGradeById(id: UUID, value: Double)

    /**
     * Get a flow list of divisions with Modules
     *
     * @return a flow list of divisions with modules or flow of empty list
     * @see Division
     */
    @Transaction
    @Query("SELECT * FROM division")
    fun getAllWithModules(): Flow<List<DivisionWithModules>>

    /**
     * get a division with modules by id
     *
     * @param id [UUID]
     * @return Optional division Entity with modules
     * @see Division
     */
    @Transaction
    @Query("SELECT * FROM division WHERE id LIKE :id")
    suspend fun getAllWithModulesById(id: UUID): DivisionWithModules?

    /**
     * get flow list of divisions by given school id
     *
     * @param id [UUID]
     * @return Flow list of divisions by school id
     * @see Division
     */
    @Transaction
    @Query("SELECT * FROM division WHERE school_id LIKE :id")
    fun getAllDivisionsFromSchoolId(id: UUID): Flow<List<Division>>
}