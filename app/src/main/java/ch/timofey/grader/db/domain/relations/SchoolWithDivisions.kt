package ch.timofey.grader.db.domain.relations

import androidx.room.Embedded
import androidx.room.Relation
import ch.timofey.grader.db.domain.division.Division
import ch.timofey.grader.db.domain.school.School

data class SchoolWithDivisions(
    @Embedded val school: School,
    @Relation(
        parentColumn = "id",
        entityColumn = "school_id"
    ) val divisions: List<Division>
)
