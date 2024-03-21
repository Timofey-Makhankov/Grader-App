package ch.timofey.grader.ui.screen.settings

import ch.timofey.grader.db.AppTheme

data class SettingsState(
    val calculatePointsState: Boolean = false,
    val doublePointsState: Boolean = false,
    val appTheme: AppTheme = AppTheme.DEVICE_THEME,
    val enableSwipeToDelete: Boolean = true
)
