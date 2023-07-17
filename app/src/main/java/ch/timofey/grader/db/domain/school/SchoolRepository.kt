package ch.timofey.grader.db.domain.school

import ch.timofey.grader.db.domain.relations.SchoolWithDivisions
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface SchoolRepository {
    suspend fun saveSchool(school: School)
    suspend fun deleteSchool(school: School)
    suspend fun updateSchool(school: School)
    suspend fun getSchoolById(id: UUID): School?
    suspend fun updateIsSelectedSchool(id: UUID, value: Boolean)
    suspend fun updateOnDeleteSchool(id: UUID, value: Boolean)
    fun getAllSchools(): Flow<List<School>>
    fun getAllSchoolsWithDivisions(): Flow<List<SchoolWithDivisions>>
    suspend fun getSchoolWithDivisionsById(id: UUID): SchoolWithDivisions?
}