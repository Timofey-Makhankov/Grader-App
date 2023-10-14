package ch.timofey.grader.db.converter

import androidx.room.TypeConverter
import java.util.UUID

class UUIDConverter {
    @TypeConverter
    fun fromUUID(uuid: UUID): String {
        return uuid.toString();
    }

    @TypeConverter
    fun uuidFromString(id: String): UUID {
        return UUID.fromString(id)
    }
}