package ch.timofey.grader.db.domain.module

import kotlinx.coroutines.flow.Flow
import java.util.UUID

class ModuleRepositoryImpl(private val moduleDao: ModuleDao): ModuleRepository {
    override suspend fun insertModule(module: Module) {
        moduleDao.insert(module)
    }

    override suspend fun deleteModule(module: Module) {
        moduleDao.delete(module)
    }

    override suspend fun getModuleById(id: UUID): Module? {
        return moduleDao.getById(id)
    }

    override fun getAllModules(): Flow<List<Module>> {
        return moduleDao.getAll()
    }
}