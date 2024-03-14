package ch.timofey.grader.db.domain.module

import androidx.room.*
import ch.timofey.grader.db.converter.UUIDSerializer
import ch.timofey.grader.db.domain.division.Division
import kotlinx.serialization.Serializable
import java.util.UUID

/**
 * This is the module entity schema for the database
 *
 * @property id unique [UUID]
 * @property divisionId [UUID] for division entity
 * @property name of the division
 * @property description of the division
 * @property teacherLastname String
 * @property teacherFirstname String
 * @property isSelected to be able to see its grade
 * @property grade average grade of previous item
 * @property onDelete to delete in the future
 */
@Entity(
    tableName = "module", foreignKeys = [ForeignKey(
        entity = Division::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("division_id"),
        onDelete = ForeignKey.CASCADE
    )]
)
@Serializable
data class Module(
    @Serializable(with = UUIDSerializer::class)
    @PrimaryKey(autoGenerate = false) val id: UUID,
    @Serializable(with = UUIDSerializer::class)
    @ColumnInfo(name = "division_id", index = true) val divisionId: UUID,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "teacher_lastname", defaultValue = "") val teacherLastname: String,
    @ColumnInfo(name = "teacher_firstname", defaultValue = "") val teacherFirstname: String,
    @ColumnInfo(name = "is_selected") val isSelected: Boolean = false,
    @ColumnInfo(name = "grade", defaultValue = "0.0") val grade: Double = 0.0,
    @ColumnInfo(name = "on_delete", defaultValue = "false") val onDelete: Boolean = false
)
