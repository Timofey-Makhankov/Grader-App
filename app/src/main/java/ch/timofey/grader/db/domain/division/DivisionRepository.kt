package ch.timofey.grader.db.domain.division

import ch.timofey.grader.db.domain.relations.DivisionWithModules
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface DivisionRepository {
    /**
     * save division entity
     *
     * @param division
     * @see Division
     */
    suspend fun saveDivision(division: Division)

    /**
     * delete division entity by id
     *
     * @param id [UUID]
     * @see Division
     */
    suspend fun deleteDivision(id: UUID)

    /**
     * update division entity
     *
     * @param division
     * @see Division
     */
    suspend fun updateDivision(division: Division)

    /**
     * get division entity by id
     *
     * @param id [UUID]
     * @return Optional Division
     * @see Division
     */
    suspend fun getDivision(id: UUID): Division?

    /**
     * Check, whether a given division exists by given id
     *
     * @param id of division
     * @return true, if division exists, otherwise false
     * @see Division
     */
    suspend fun existsById(id: UUID): Boolean

    /**
     * get the school name from
     *
     * @param schoolId
     * @return name of school
     * @see ch.timofey.grader.db.domain.school.School
     */
    suspend fun getSchoolName(schoolId: UUID): String

    /**
     * update isSelected field in division entity by id
     *
     * @param id [UUID]
     * @param value Boolean
     * @see Division
     */
    suspend fun updateIsSelectedDivision(id: UUID, value: Boolean)

    /**
     * update onDelete field in division entity by id
     *
     * @param id
     * @param value
     */
    suspend fun updateOnDeleteDivision(id: UUID, value: Boolean)

    /**
     * update average grade for school entity by id
     *
     * @param id [UUID]
     * @param value Double
     */
    suspend fun updateSchoolGradeById(id: UUID, value: Double)

    /**
     * get division entity with modules by id
     *
     * @param id [UUID]
     * @return Optional Division with Modules
     */
    suspend fun getWithModulesById(id: UUID): DivisionWithModules?

    /**
     * get a flow list of division entities
     *
     * @return a flow of list divisions or an empty list
     * @see Division
     */
    fun getAllDivisionsFlow(): Flow<List<Division>>

    /**
     * get all divisions
     *
     * @return a list of divisions or an empty list
     * @see Division
     */
    fun getAllDivisions(): List<Division>

    /**
     * get a flow list of division entities with Modules
     *
     * @return a flow of list divisions with modules or an empty list
     */
    fun getAllWithModules(): Flow<List<DivisionWithModules>>

    /**
     *  get division entity with modules flow by id
     *
     * @param id [UUID]
     * @return Optional Division with Modules flow
     */
    fun getAllDivisionsFromSchoolId(id: UUID): Flow<List<Division>>
}