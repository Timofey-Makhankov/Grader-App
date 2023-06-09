package ch.timofey.grader.db.domain.division

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "division")
data class Division(
    @PrimaryKey(autoGenerate = false) val id: UUID,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "school_year", defaultValue = "0") val schoolYear: Int,
    @ColumnInfo(name = "school_id") val schoolId: UUID,
    @ColumnInfo(name = "is_selected") val isSelected: Boolean = false,
    @ColumnInfo(name = "grade", defaultValue = "0.0") val grade: Double = 0.0,
)
