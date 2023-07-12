package ch.timofey.grader.db.domain.module

import ch.timofey.grader.db.domain.relations.ModuleWithExams
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface ModuleRepository {
    suspend fun saveModule(module: Module)
    suspend fun deleteModule(module: Module)
    suspend fun getModuleById(id: UUID): Module?
    suspend fun updateIsSelectedModule(id: UUID, value: Boolean)
    suspend fun updateDivisionGradeById(id: UUID, value: Double)
    fun getAllModules(): Flow<List<Module>>
    fun getAllModulesWithExams(): Flow<List<ModuleWithExams>>
    suspend fun getModuleWithExamsById(id: UUID): ModuleWithExams?
}