package ch.timofey.grader.db.domain.school

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

/**
 * This is the School Entity schema for the database
 *
 * @property id unique [UUID]
 * @property name String of School name
 * @property description String description of the School
 * @property address String address with number
 * @property zipCode String Zipcode
 * @property city String City
 * @property isSelected To able to see it's grade
 * @property grade average grade of previous items
 * @property onDelete to delete entity in future
 */
@Entity(tableName = "school")
data class School(
    @PrimaryKey(autoGenerate = false) val id: UUID,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "address") val address: String,
    @ColumnInfo(name = "zip") val zipCode: String,
    @ColumnInfo(name = "city") val city: String,
    @ColumnInfo(name = "is_selected") val isSelected: Boolean = false,
    @ColumnInfo(name = "grade", defaultValue = "0.0") val grade: Double = 0.0,
    @ColumnInfo(name = "on_delete", defaultValue = "false") val onDelete: Boolean = false
)