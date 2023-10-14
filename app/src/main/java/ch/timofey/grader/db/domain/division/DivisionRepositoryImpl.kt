package ch.timofey.grader.db.domain.division

import android.util.Log
import ch.timofey.grader.db.domain.relations.DivisionWithModules
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class DivisionRepositoryImpl(private val divisionDao: DivisionDao) : DivisionRepository {
    private val tag: String = "DivisionRepositoryImpl"
    override suspend fun saveDivision(division: Division) {
        Log.v(tag, "trying to save division")
        divisionDao.save(division)
        Log.d(tag, "division saved")
    }

    override suspend fun deleteDivision(id: UUID) {
        Log.v(tag, "trying to delete division")
        divisionDao.delete(id)
        Log.d(tag, "division deleted")
    }

    override suspend fun updateDivision(division: Division) {
        Log.v(tag, "trying to update division")
        divisionDao.update(division)
        Log.d(tag, "division updated")
    }

    override suspend fun getDivision(id: UUID): Division? {
        Log.v(tag, "trying to find division with id: $id")
        val result = divisionDao.getById(id)
        Log.d(tag, "optional division found")
        return result
    }

    override fun getAllDivisionsFlow(): Flow<List<Division>> {
        Log.v(tag, "trying to get list flow of divisions")
        val result = divisionDao.getAllFlow()
        Log.d(tag, "got flow list of divisions")
        return result
    }

    override fun getAllDivisions(): List<Division> {
        Log.v(tag, "trying to get list of divisions")
        val result = divisionDao.getAll()
        Log.d(tag, "got list of divisions")
        return result
    }

    override suspend fun updateIsSelectedDivision(id: UUID, value: Boolean) {
        Log.v(tag, "trying to update isSelected with id: $id with value: $value")
        divisionDao.updateIsSelected(id, value)
        Log.d(tag, "isSelected updated")
    }

    override suspend fun updateOnDeleteDivision(id: UUID, value: Boolean) {
        Log.v(tag, "trying to update onDelete with id: $id with value: $value")
        divisionDao.updateOnDelete(id, value)
        Log.d(tag, "onDelete updated")
    }

    override suspend fun updateSchoolGradeById(id: UUID, value: Double) {
        Log.v(tag, "trying to update school grade of id: $id with value: $value")
        divisionDao.updateSchoolGradeById(id, value)
        Log.d(tag, "school grade updated")
    }

    override fun getAllWithModules(): Flow<List<DivisionWithModules>> {
        Log.v(tag, "trying to get flow list divisions with modules")
        val result = divisionDao.getAllWithModules()
        Log.d(tag, "got flow list divisions with modules")
        return result
    }

    override suspend fun getWithModulesById(id: UUID): DivisionWithModules? {
        Log.v(tag, "trying to get division with modules with id: $id")
        val result = divisionDao.getAllWithModulesById(id)
        Log.d(tag, "got Optional Division with modules")
        return result
    }

    override fun getAllDivisionsFromSchoolId(id: UUID): Flow<List<Division>> {
        Log.v(tag, "trying to get flow divisions from school id: $id")
        val result = divisionDao.getAllDivisionsFromSchoolId(id)
        Log.d(tag, "got flow of divisions from school")
        return result
    }
}