package ch.timofey.grader.db.domain.division

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "division")
data class Division(
    @PrimaryKey val id: UUID,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "teacher_lastname") val teacherLastname: String,
    @ColumnInfo(name = "teacher_firstname") val teacherFirstname: String,
    @ColumnInfo(name ="school_id") val schoolId: UUID
)