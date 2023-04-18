package ch.timofey.grader.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Grade(
    @PrimaryKey val id: Int
)
