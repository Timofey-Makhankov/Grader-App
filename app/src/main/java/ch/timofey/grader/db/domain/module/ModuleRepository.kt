package ch.timofey.grader.db.domain.module

import ch.timofey.grader.db.domain.relations.ModuleWithExams
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface ModuleRepository {
    /**
     * save module entity
     *
     * @param module entity
     * @see Module
     */
    suspend fun saveModule(module: Module)

    /**
     * delete module entity by id
     *
     * @param id [UUID]
     * @see Module
     */
    suspend fun deleteModuleById(id: UUID)

    /**
     * update module entity
     *
     * @param module entity
     * @see Module
     */
    suspend fun updateModule(module: Module)

    /**
     * get module entity by id
     *
     * @param id [UUID]
     * @return Optional [Module] Entity
     * @see Module
     */
    suspend fun getModuleById(id: UUID): Module?

    /**
     * update isSelected on Module Entity by id and value
     *
     * @param id [UUID]
     * @param value boolean
     * @see Module
     */
    suspend fun updateIsSelectedModule(id: UUID, value: Boolean)
    /**
     * update onDelete on Module Entity by id and value
     *
     * @param id [UUID]
     * @param value boolean
     * @see Module
     */
    suspend fun updateOnDeleteModule(id: UUID, value: Boolean)

    /**
     * update average grade to parent division entity
     *
     * @param id division [UUID]
     * @param value Double
     */
    suspend fun updateDivisionGradeById(id: UUID, value: Double)

    /**
     * get flow of list modules
     *
     * @return flow list of modules or empty flow list
     * @see Module
     */
    fun getAllFlowModules(): Flow<List<Module>>

    /**
     * get list of modules
     *
     * @return list of modules or empty list
     * @see Module
     */
    fun getAllModules(): List<Module>

    /**
     * get flow list of modules with exams
     *
     * @return flow list of modules with exams or empty flow list
     * @see Module
     */
    fun getAllModulesWithExams(): Flow<List<ModuleWithExams>>

    /**
     * get flow list of modules by division id
     *
     * @param id division [UUID]
     * @return flow list of modules or empty flow list
     * @see Module
     */
    fun getAllModulesFromDivisionId(id: UUID): Flow<List<Module>>

    /**
     * get a module with exams by id
     *
     * @param id [UUID]
     * @return optional module with exams
     * @see Module
     */
    suspend fun getModuleWithExamsById(id: UUID): ModuleWithExams?
}