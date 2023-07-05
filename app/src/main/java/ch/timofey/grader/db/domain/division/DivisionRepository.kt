package ch.timofey.grader.db.domain.division

import ch.timofey.grader.db.domain.relations.DivisionWithModules
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface DivisionRepository {
    suspend fun saveDivision(division: Division)
    suspend fun deleteDivision(division: Division)
    suspend fun getDivision(id: UUID): Division?
    fun getAllDivisions(): Flow<List<Division>>
    suspend fun updateIsSelectedDivision(id: UUID, value: Boolean)
    fun getAllWithModules(): Flow<List<DivisionWithModules>>
    suspend fun getWithModulesById(id: UUID): DivisionWithModules?
    fun getAllDivisionsFromSchoolId(id: UUID): Flow<List<Division>>
}