package ch.timofey.grader.db.domain.module

import android.util.Log
import ch.timofey.grader.db.domain.relations.ModuleWithExams
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class ModuleRepositoryImpl(private val moduleDao: ModuleDao) : ModuleRepository {
    private val tag: String = "ModuleRepositoryImpl"
    override suspend fun saveModule(module: Module) {
        Log.v(tag, "trying to save module")
        moduleDao.save(module)
        Log.d(tag, "module saved")
    }

    override suspend fun deleteModuleById(id: UUID) {
        Log.v(tag, "trying to delete module by id: $id")
        moduleDao.deleteById(id)
        Log.d(tag, "module deleted")
    }

    override suspend fun updateModule(module: Module) {
        Log.v(tag, "trying to update a module")
        moduleDao.update(module)
        Log.d(tag, "module updated")
    }

    override suspend fun getModuleById(id: UUID): Module? {
        Log.v(tag, "trying to get a module by id: $id")
        val result = moduleDao.getById(id)
        Log.d(tag, "optional module found")
        return result
    }

    override suspend fun updateIsSelectedModule(id: UUID, value: Boolean) {
        Log.v(tag, "trying to update isSelected by id: $id and value: $value")
        moduleDao.updateIsSelected(id, value)
        Log.d(tag, "isSelected updated")
    }

    override suspend fun updateOnDeleteModule(id: UUID, value: Boolean) {
        Log.v(tag, "trying to update onDelete by id: $id and value: $value")
        moduleDao.updateOnDelete(id, value)
        Log.d(tag, "onDelete updated")
    }

    override suspend fun updateDivisionGradeById(id: UUID, value: Double) {
        Log.v(tag, "trying to update average grade on parent division id: $id and grade: $value")
        moduleDao.updateDivisionGradeById(id, value)
        Log.d(tag, "average grade updated")
    }

    override fun getAllFlowModules(): Flow<List<Module>> {
        Log.v(tag, "trying to get a flow of list modules")
        val result = moduleDao.getAllFlow()
        Log.d(tag, "flow list modules found")
        return result
    }

    override fun getAllModules(): List<Module> {
        Log.v(tag, "trying to get a list of modules")
        val result = moduleDao.getAll()
        Log.d(tag, "list of modules found")
        return result
    }

    override fun getAllModulesWithExams(): Flow<List<ModuleWithExams>> {
        Log.v(tag, "trying to get flow list modules with exams")
        val result = moduleDao.getAllWithExams()
        Log.d(tag, "flow list modules with exams found")
        return result
    }

    override fun getAllModulesFromDivisionId(id: UUID): Flow<List<Module>> {
        Log.v(tag, "trying to get flow list modules by division id: $id")
        val result = moduleDao.getAllModulesFromDivisionId(id)
        Log.d(tag, "flow list modules found")
        return result
    }

    override suspend fun getModuleWithExamsById(id: UUID): ModuleWithExams? {
        Log.v(tag, "trying to get module with exams by id: $id")
        val result = moduleDao.getWithExamsById(id)
        Log.d(tag, "Optional Module with exams found")
        return result
    }
}