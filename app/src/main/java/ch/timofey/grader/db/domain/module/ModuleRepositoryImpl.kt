package ch.timofey.grader.db.domain.module

import ch.timofey.grader.db.domain.relations.ModuleWithExams
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class ModuleRepositoryImpl(private val moduleDao: ModuleDao) : ModuleRepository {
    override suspend fun saveModule(module: Module) {
        moduleDao.save(module)
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

    override fun getAllModulesWithExams(): Flow<List<ModuleWithExams>> {
        return moduleDao.getAllWithExams()
    }

    override suspend fun getModuleWithExamsById(id: UUID): ModuleWithExams? {
        return moduleDao.getWithExamsById(id)
    }
}