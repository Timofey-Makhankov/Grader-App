package ch.timofey.grader.db.domain.school

import ch.timofey.grader.db.domain.relations.SchoolWithDivisions
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class SchoolRepositoryImpl(private val schoolDao: SchoolDao) : SchoolRepository {
    override suspend fun saveSchool(school: School) {
        schoolDao.save(school)
    }

    override suspend fun deleteSchool(school: School) {
        schoolDao.delete(school)
    }

    override suspend fun getSchoolById(id: UUID): School? {
        return schoolDao.getById(id)
    }

    override suspend fun updateIsSelectedSchool(id: UUID, value: Boolean) {
        return schoolDao.updateIsSelected(id, value)
    }

    override fun getAllSchools(): Flow<List<School>> {
        return schoolDao.getAll()
    }

    override fun getAllSchoolsWithDivisions(): Flow<List<SchoolWithDivisions>> {
        return schoolDao.getAllWithDivisions()
    }

    override suspend fun getSchoolWithModulesById(id: UUID): SchoolWithDivisions? {
        return schoolDao.getWithExamsById(id)
    }
}