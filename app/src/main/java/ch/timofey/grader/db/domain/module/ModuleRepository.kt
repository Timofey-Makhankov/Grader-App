package ch.timofey.grader.db.domain.module

import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface ModuleRepository {
    suspend fun insertModule(module: Module)

    suspend fun deleteModule(module: Module)

    suspend fun getModuleById(id: UUID): Module?

    fun getAllModules(): Flow<List<Module>>
}