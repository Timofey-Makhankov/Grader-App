package ch.timofey.grader.ui.screen.settings

import ch.timofey.grader.db.AppTheme
import ch.timofey.grader.type.AppLanguage
import ch.timofey.grader.type.DateFormatting

data class SettingsState(
    val calculatePointsState: Boolean = false,
    val doublePointsState: Boolean = false,
    val appTheme: AppTheme = AppTheme.DEVICE_THEME,
    val enableSwipeToDelete: Boolean = false,
    val minimumGrade: String = "4.0",
    val validMinimumGrade: Boolean = true,
    val language: AppLanguage = AppLanguage.ENGLISH,
    val dateFormat: DateFormatting = DateFormatting.DEFAULT,
    val showNavigationIcons: Boolean = false,
    val colorGrades: Boolean = false,
    val swapNavigation: Boolean = false,
    val errorMessageMinimumGrade: String = "",
)
