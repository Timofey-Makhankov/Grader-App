package ch.timofey.grader.db.domain.school

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "school")
data class School(
    @PrimaryKey(autoGenerate = false) val id: UUID,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "address") val address: String,
    @ColumnInfo(name = "zip") val zipCode: String,
    @ColumnInfo(name = "city") val city: String
)