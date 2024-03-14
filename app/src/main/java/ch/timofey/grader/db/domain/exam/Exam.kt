package ch.timofey.grader.db.domain.exam

import androidx.room.*
import ch.timofey.grader.db.converter.DateSerializer
import ch.timofey.grader.db.converter.UUIDSerializer
import ch.timofey.grader.db.domain.module.Module
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.util.UUID

/**
 * This is the Exam entity schema for the database
 *
 * @property id unique [UUID]
 * @property moduleId [UUID] for module entity
 * @property name of the exam
 * @property description of the exam
 * @property grade of the exam
 * @property weight of the exam
 * @property date when exam taken
 * @property isSelected to be able to see its grade
 * @property onDelete to delete in the future
 */
@Entity(
    tableName = "exam", foreignKeys = [ForeignKey(
        entity = Module::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("module_id"),
        onDelete = ForeignKey.CASCADE
    )]
)
@Serializable
data class Exam(
    @Serializable(with = UUIDSerializer::class)
    @PrimaryKey(autoGenerate = false) val id: UUID,
    @Serializable(with = UUIDSerializer::class)
    @ColumnInfo(name = "module_id", index = true) val moduleId: UUID,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "grade") val grade: Double,
    @ColumnInfo(name = "weight") val weight: Double,
    @Serializable(with = DateSerializer::class)
    @ColumnInfo(name = "date") val date: LocalDate,
    @ColumnInfo(name = "is_selected") val isSelected: Boolean = false,
    @ColumnInfo(name = "on_delete", defaultValue = "false") val onDelete: Boolean = false,
)
