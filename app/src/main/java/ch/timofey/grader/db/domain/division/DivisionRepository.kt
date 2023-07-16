package ch.timofey.grader.db.domain.division

import ch.timofey.grader.db.domain.relations.DivisionWithModules
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface DivisionRepository {
    suspend fun saveDivision(division: Division)
    suspend fun deleteDivision(division: Division)
    suspend fun updateDivision(division: Division)
    suspend fun getDivision(id: UUID): Division?
    suspend fun updateIsSelectedDivision(id: UUID, value: Boolean)
    suspend fun updateOnDeleteDivision(id: UUID, value: Boolean)
    suspend fun updateSchoolGradeById(id: UUID, value: Double)
    suspend fun getWithModulesById(id: UUID): DivisionWithModules?
    fun getAllDivisions(): Flow<List<Division>>
    fun getAllWithModules(): Flow<List<DivisionWithModules>>
    fun getAllDivisionsFromSchoolId(id: UUID): Flow<List<Division>>
}