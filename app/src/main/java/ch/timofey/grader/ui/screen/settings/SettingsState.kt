package ch.timofey.grader.ui.screen.settings

import ch.timofey.grader.db.AppTheme
import ch.timofey.grader.utils.AppLanguage

data class SettingsState(
    val calculatePointsState: Boolean = false,
    val doublePointsState: Boolean = false,
    val appTheme: AppTheme = AppTheme.DEVICE_THEME,
    val enableSwipeToDelete: Boolean = false,
    val minimumGrade: String = "4.0",
    val validMinimumGrade: Boolean = true,
    val language: AppLanguage = AppLanguage.ENGLISH,
    val errorMessageMinimumGrade: String = "",
)
