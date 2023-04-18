package ch.timofey.grader.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subject")
data class Subject(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "subject_name") val subjectName: String
)
