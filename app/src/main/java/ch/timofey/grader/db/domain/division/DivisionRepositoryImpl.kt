package ch.timofey.grader.db.domain.division

import kotlinx.coroutines.flow.Flow
import java.util.UUID

class DivisionRepositoryImpl(private val divisionDao: DivisionDao): DivisionRepository{
    override suspend fun insertDivision(division: Division) {
        divisionDao.insert(division)
    }

    override suspend fun deleteDivision(division: Division) {
        divisionDao.delete(division)
    }

    override suspend fun getDivision(id: UUID): Division? {
        return divisionDao.getById(id)
    }

    override fun getAllDivisions(): Flow<List<Division>> {
        return divisionDao.getAll()
    }
}