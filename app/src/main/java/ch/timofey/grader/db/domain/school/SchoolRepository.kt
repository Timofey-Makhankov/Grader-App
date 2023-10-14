package ch.timofey.grader.db.domain.school

import ch.timofey.grader.db.domain.relations.SchoolWithDivisions
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface SchoolRepository {
    /**
     * Saves a school entity to the database
     *
     * @param school school Entity
     * @see School
     */
    suspend fun saveSchool(school: School)

    /**
     * deletes a school entity in the database
     *
     * @param id school [UUID]
     * @see School
     */
    suspend fun deleteSchool(id: UUID)

    /**
     * update a school entity in the database
     *
     * @param school entity with an school id that exists
     * @see School
     */
    suspend fun updateSchool(school: School)

    /**
     * get a school entity from given id
     *
     * @param id [UUID]
     * @return school entity
     * @see School
     */
    suspend fun getSchoolById(id: UUID): School?

    /**
     * update isSelected field with given school id and value
     *
     * @param id [UUID]
     * @param value to be set to
     * @see School
     */
    fun updateIsSelectedSchool(id: UUID, value: Boolean)

    /**
     * update onDelete field with given school id and value
     *
     * @param id [UUID]
     * @param value to be set to
     * @see School
     */
    fun updateOnDeleteSchool(id: UUID, value: Boolean)

    /**
     * Get a Flow list of School entities
     *
     * @return a list of [School]s or an empty list, when there were no schools found
     * @see School
     */
    fun getAllSchoolFlows(): Flow<List<School>>

    /**
     * Get a list of school entities
     *
     * @return a list of [School]s or an empty list, when there were no schools found
     */
    fun getAllSchools(): List<School>

    /**
     * Get a Flow list of School entities with Divisions
     *
     * @return a list of [School]s or an empty list, when there were no schools found
     */
    fun getAllSchoolsWithDivisions(): Flow<List<SchoolWithDivisions>>

    /**
     * get a school entity from given id
     *
     * @param id [UUID]
     * @return an Optional [School] entity
     */
    suspend fun getSchoolWithDivisionsById(id: UUID): SchoolWithDivisions?
}