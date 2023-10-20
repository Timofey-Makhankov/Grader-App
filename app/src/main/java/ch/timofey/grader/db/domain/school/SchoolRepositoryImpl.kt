package ch.timofey.grader.db.domain.school

import android.util.Log
import ch.timofey.grader.db.domain.relations.SchoolWithDivisions
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class SchoolRepositoryImpl(private val schoolDao: SchoolDao) : SchoolRepository {
    private val tag: String = "SchoolRepositoryImpl"
    override suspend fun saveSchool(school: School) {
        Log.v(tag, "school trying to be saved")
        schoolDao.save(school)
        Log.d(tag, "school saved")
    }

    override suspend fun deleteSchool(id: UUID) {
        Log.v(tag, "trying to delete school")
        schoolDao.deleteById(id)
        Log.d(tag, "school deleted")
    }

    override suspend fun updateSchool(school: School) {
        Log.v(tag, "trying to update School")
        schoolDao.update(school)
        Log.d(tag, "school updated")
    }

    override suspend fun getSchoolById(id: UUID): School? {
        Log.v(tag, "tried to access school with Id: $id")
        val result = schoolDao.getById(id)
        Log.d(tag, "School was found with Id: $id")
        return result
    }

    override fun updateIsSelectedSchool(id: UUID, value: Boolean) {
        Log.v(tag, "trying to update school with Id: $id; and value: $value")
        schoolDao.updateIsSelected(id, value)
        Log.d(tag, "updated is selected School with Id: $id; and value: $value")
    }

    override fun updateOnDeleteSchool(id: UUID, value: Boolean) {
        Log.v(tag, "trying to update on delete with id: $id; and value: $value")
        schoolDao.updateOnDelete(id, value)
        Log.d(tag, "updated on delete with id: $id; and value: $value\"")
    }

    override fun getAllSchoolFlows(): Flow<List<School>> {
        Log.v(tag, "trying to get all Flow schools")
        val result = schoolDao.getAllWithFlow()
        Log.d(tag, "schools have been found")
        return result
    }

    override fun getAllSchools(): List<School> {
        Log.v(tag, "trying to get all schools")
        val result = schoolDao.getAll()
        Log.d(tag, "schools have been found")
        return result
    }

    override fun getAllSchoolsWithDivisions(): Flow<List<SchoolWithDivisions>> {
        Log.v(tag, "trying to get all schools with their divisions")
        val result = schoolDao.getAllWithDivisions()
        Log.d(tag, "schools with divisions have been found")
        return result
    }

    override suspend fun getSchoolWithDivisionsById(id: UUID): SchoolWithDivisions? {
        Log.v(tag, "trying to access school with Divisions with Id: $id")
        val result = schoolDao.getWithDivisionsById(id)
        Log.d(tag, "School with divisions has been found")
        return result
    }
}