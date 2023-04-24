package ch.timofey.grader.db.domain.school

import kotlinx.coroutines.flow.Flow
import java.util.UUID

class SchoolRepositoryImpl(private val schoolDao: SchoolDao): SchoolRepository {
    override suspend fun insertSchool(school: School) {
        schoolDao.insert(school)
    }

    override suspend fun deleteSchool(school: School) {
        schoolDao.delete(school)
    }

    override suspend fun getSchoolById(id: UUID): School? {
        return schoolDao.getById(id)
    }

    override fun getAllSchools(): Flow<List<School>> {
        return schoolDao.getAll()
    }
}