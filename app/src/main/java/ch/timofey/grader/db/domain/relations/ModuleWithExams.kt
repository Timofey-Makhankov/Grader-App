package ch.timofey.grader.db.domain.relations

import androidx.room.Embedded
import androidx.room.Relation
import ch.timofey.grader.db.domain.exam.Exam
import ch.timofey.grader.db.domain.module.Module

data class ModuleWithExams(
    @Embedded val module: Module, @Relation(
        parentColumn = "id", entityColumn = "module_id"
    ) val exams: List<Exam>
)
