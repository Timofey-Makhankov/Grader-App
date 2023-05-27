package ch.timofey.grader.db.domain.module

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "module")
data class Module(
    @PrimaryKey(autoGenerate = false) val id: UUID,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "division_id") val divisionId: UUID,
    @ColumnInfo(name = "is_selected") val isSelected: Boolean = false,
)
