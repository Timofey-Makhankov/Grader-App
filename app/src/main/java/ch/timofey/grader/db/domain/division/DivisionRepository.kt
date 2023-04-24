package ch.timofey.grader.db.domain.division

import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface DivisionRepository {
    suspend fun insertDivision(division: Division)

    suspend fun deleteDivision(division: Division)

    suspend fun getDivision(id: UUID): Division?

    fun getAllDivisions(): Flow<List<Division>>
}