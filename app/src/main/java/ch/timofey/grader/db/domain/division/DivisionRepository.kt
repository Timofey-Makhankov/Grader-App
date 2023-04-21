package ch.timofey.grader.db.domain.division

import androidx.lifecycle.LiveData
import java.util.UUID

class DivisionRepository(private val divisionDao: DivisionDao) {
    suspend fun insertDivision(division: Division) =
        divisionDao.insert(division)

    suspend fun deleteDivision(division: Division) =
        divisionDao.delete(division)

    fun getDivision(id: UUID): Division =
        divisionDao.getById(id)

    fun getAllDivisions(): LiveData<List<Division>> =
        divisionDao.getAll()
}