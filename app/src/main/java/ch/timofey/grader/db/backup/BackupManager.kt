package ch.timofey.grader.db.backup

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class BackupManager {
    fun createBackup(data: BackupData): String{
        return Json.encodeToString(BackupData.serializer(), data)
    }
    fun readBackup(data: String): BackupData{
        return Json.decodeFromString(data)
    }
}