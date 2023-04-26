package ch.timofey.grader.db.domain.relations

import androidx.room.Embedded
import androidx.room.Relation
import ch.timofey.grader.db.domain.division.Division
import ch.timofey.grader.db.domain.module.Module

data class DivisionWithModules(
    @Embedded val division: Division,
    @Relation(
        parentColumn = "id",
        entityColumn = "division_id"
    )
    val modules: List<Module>
)
