package ch.timofey.grader.db.domain.exam

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity(tableName = "exam")
data class Exam(
    @PrimaryKey(autoGenerate = false) val id: UUID,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "grade") val grade: Double,
    @ColumnInfo(name = "weight") val weight: Double,
    @ColumnInfo(name = "date") val date: Date,
    @ColumnInfo(name = "module_id") val module: UUID,
    @ColumnInfo(name = "is_selected") val isSelected: Boolean = false,
)
