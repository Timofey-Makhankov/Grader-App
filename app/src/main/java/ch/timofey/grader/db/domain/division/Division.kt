package ch.timofey.grader.db.domain.division

import androidx.room.*
import ch.timofey.grader.db.domain.school.School
import kotlinx.serialization.Serializable
import java.util.UUID

/**
 * This is the division entity schema for the database
 *
 * @property id unique [UUID]
 * @property schoolId [UUID] of school entity
 * @property name of division
 * @property description of division
 * @property schoolYear year for division
 * @property isSelected to be able to see it's grade
 * @property grade average grade of previous item
 * @property onDelete to delete in the future
 */
@Entity(
    tableName = "division", foreignKeys = [ForeignKey(
        entity = School::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("school_id"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class Division(
    @PrimaryKey(autoGenerate = false) val id: UUID,
    @ColumnInfo(name = "school_id", index = true) val schoolId: UUID,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "school_year", defaultValue = "0") val schoolYear: Int,
    @ColumnInfo(name = "is_selected") val isSelected: Boolean = false,
    @ColumnInfo(name = "grade", defaultValue = "0.0") val grade: Double = 0.0,
    @ColumnInfo(name = "on_delete", defaultValue = "false") val onDelete: Boolean = false
)
