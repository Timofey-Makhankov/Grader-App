package ch.timofey.grader.db.domain.division

import ch.timofey.grader.db.domain.relations.DivisionWithModules
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class DivisionRepositoryImpl(private val divisionDao: DivisionDao) : DivisionRepository {
    override suspend fun saveDivision(division: Division) {
        divisionDao.save(division)
    }

    override suspend fun deleteDivision(division: Division) {
        divisionDao.delete(division)
    }

    override suspend fun updateDivision(division: Division) {
        divisionDao.update(division)
    }

    override suspend fun getDivision(id: UUID): Division? {
        return divisionDao.getById(id)
    }

    override fun getAllDivisions(): Flow<List<Division>> {
        return divisionDao.getAll()
    }

    override suspend fun updateIsSelectedDivision(id: UUID, value: Boolean) {
        divisionDao.updateIsSelected(id, value)
    }

    override suspend fun updateOnDeleteDivision(id: UUID, value: Boolean) {
        divisionDao.updateOnDelete(id, value)
    }

    override suspend fun updateSchoolGradeById(id: UUID, value: Double) {
        divisionDao.updateSchoolGradeById(id, value)
    }

    override fun getAllWithModules(): Flow<List<DivisionWithModules>> {
        return divisionDao.getAllWithModules()
    }

    override suspend fun getWithModulesById(id: UUID): DivisionWithModules? {
        return divisionDao.getAllWithModulesById(id)
    }

    override fun getAllDivisionsFromSchoolId(id: UUID): Flow<List<Division>> {
        return divisionDao.getAllDivisionsFromSchoolId(id)
    }
}