package ch.timofey.grader.db.domain.exam

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import ch.timofey.grader.db.domain.module.Module
import java.time.LocalDate
import java.util.UUID

@Entity(
    tableName = "exam",
    foreignKeys = [
        ForeignKey(
            entity = Module::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("module_id"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Exam(
    @PrimaryKey(autoGenerate = false) val id: UUID,
    @ColumnInfo(name = "module_id", index = true) val module: UUID,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "grade") val grade: Double,
    @ColumnInfo(name = "weight") val weight: Double,
    @ColumnInfo(name = "date") val date: LocalDate,
    @ColumnInfo(name = "is_selected") val isSelected: Boolean = false,
    @ColumnInfo(name = "on_delete", defaultValue = "false") val onDelete: Boolean = false,
)
