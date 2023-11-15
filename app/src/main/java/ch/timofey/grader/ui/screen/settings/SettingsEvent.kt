package ch.timofey.grader.ui.screen.settings

import ch.timofey.grader.db.AppTheme

sealed class SettingsEvent {
    data class OnThemeChange(val theme: AppTheme) : SettingsEvent()
    data class OnCalculatePointsChange(val value: Boolean) : SettingsEvent()
    data class OnDoublePointsChange(val value: Boolean) : SettingsEvent()
    data object OnDeleteDatabaseButtonClick : SettingsEvent()
    data object OnCreateExportClick : SettingsEvent()
}