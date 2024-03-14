package ch.timofey.grader.db.backup

import ch.timofey.grader.db.domain.division.Division
import ch.timofey.grader.db.domain.exam.Exam
import ch.timofey.grader.db.domain.module.Module
import ch.timofey.grader.db.domain.school.School
import kotlinx.serialization.Serializable

@Serializable
data class BackupData(
    val schools: List<School>,
    val divisions: List<Division>,
    val modules: List<Module>,
    val exams: List<Exam>
)
