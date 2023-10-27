package ch.timofey.grader.db

import kotlinx.serialization.Serializable

@Serializable
data class AppSettings(
    val theme: AppTheme = AppTheme.DEVICE_THEME,
    val calculatePoints: Boolean = false,
    val doublePoints: Boolean = false
)
enum class AppTheme {
    DARK_MODE,
    LIGHT_MODE,
    MATERIAL_THEME,
    DEVICE_THEME
}