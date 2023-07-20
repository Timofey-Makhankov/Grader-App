package ch.timofey.grader.db.domain.module

import androidx.room.*
import ch.timofey.grader.db.domain.division.Division
import java.util.UUID

@Entity(
    tableName = "module", foreignKeys = [ForeignKey(
        entity = Division::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("division_id"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class Module(
    @PrimaryKey(autoGenerate = false) val id: UUID,
    @ColumnInfo(name = "division_id", index = true) val divisionId: UUID,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "teacher_lastname", defaultValue = "") val teacherLastname: String,
    @ColumnInfo(name = "teacher_firstname", defaultValue = "") val teacherFirstname: String,
    @ColumnInfo(name = "is_selected") val isSelected: Boolean = false,
    @ColumnInfo(name = "grade", defaultValue = "0.0") val grade: Double = 0.0,
    @ColumnInfo(name = "on_delete", defaultValue = "false") val onDelete: Boolean = false
)
