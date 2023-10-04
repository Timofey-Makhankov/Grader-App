package ch.timofey.grader.db.domain.school

import android.util.Log
import ch.timofey.grader.db.domain.relations.SchoolWithDivisions
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class SchoolRepositoryImpl(private val schoolDao: SchoolDao) : SchoolRepository {
    private val tag: String = "SchoolRepositoryImpl"
    override suspend fun saveSchool(school: School) {
        schoolDao.save(school)
        Log.d(tag, "school saved")
    }

    override suspend fun deleteSchool(school: School) {
        schoolDao.delete(school)
        Log.d(tag, "school deleted")
    }

    override suspend fun updateSchool(school: School) {
        schoolDao.update(school)
        Log.d(tag, "school updated")
    }

    override suspend fun getSchoolById(id: UUID): School? {
        Log.d(tag, "tried to access school by id")
        return schoolDao.getById(id)
    }

    override fun updateIsSelectedSchool(id: UUID, value: Boolean) {
        schoolDao.updateIsSelected(id, value)
        Log.d(tag, "updated is selected")
    }

    override fun updateOnDeleteSchool(id: UUID, value: Boolean) {
        schoolDao.updateOnDelete(id, value)
        Log.d(tag, "updated on delete")
    }

    override fun getAllSchools(): Flow<List<School>> {
        Log.d(tag, "tried to access all schools")
        return schoolDao.getAll()
    }

    override fun getAllSchoolsWithDivisions(): Flow<List<SchoolWithDivisions>> {
        Log.d(tag, "tried to access all schools with their divisions")
        return schoolDao.getAllWithDivisions()
    }

    override suspend fun getSchoolWithDivisionsById(id: UUID): SchoolWithDivisions? {
        Log.d(tag, "tried to access school with Divisions by Id")
        return schoolDao.getWithDivisionsById(id)
    }
}