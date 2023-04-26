package ch.timofey.grader.db.domain.school

import ch.timofey.grader.db.domain.relations.SchoolWithDivisions
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface SchoolRepository {
    suspend fun saveSchool(school: School)
    suspend fun deleteSchool(school: School)
    suspend fun getSchoolById(id: UUID): School?
    fun getAllSchools(): Flow<List<School>>
    suspend fun getAllSchoolsWithDivisions(): Flow<List<SchoolWithDivisions>>
    suspend fun getSchoolWithModulesById(id: UUID): SchoolWithDivisions?
}