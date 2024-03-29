package ch.timofey.grader.ui.screen.settings

import ch.timofey.grader.db.AppTheme
import java.io.InputStream
import java.io.OutputStream

sealed class SettingsEvent {
    data class OnThemeChange(val theme: AppTheme) : SettingsEvent()
    data class OnCalculatePointsChange(val value: Boolean) : SettingsEvent()
    data class OnDoublePointsChange(val value: Boolean) : SettingsEvent()
    data class OnEnableSwipeToDeleteChange(val value: Boolean): SettingsEvent()
    data class OnLoadBackupFile(val file: InputStream): SettingsEvent()
    data class OnCreateBackupFile(val file: OutputStream): SettingsEvent()
    data object OnDeleteDatabaseButtonClick : SettingsEvent()
}