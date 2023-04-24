package ch.timofey.grader.db.domain.school

import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface SchoolRepository {
    suspend fun insertSchool(school: School)

    suspend fun deleteSchool(school: School)

    suspend fun getSchoolById(id: UUID): School?

    fun getAllSchools(): Flow<List<School>>
}