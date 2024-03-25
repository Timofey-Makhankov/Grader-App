package ch.timofey.grader.db

import kotlinx.serialization.Serializable

@Serializable
data class AppSettings(
    val theme: AppTheme = AppTheme.DEVICE_THEME,
    val calculatePoints: Boolean = false,
    val doublePoints: Boolean = false,
    val enableSwipeToDelete: Boolean = true,
)
enum class AppTheme(val title: String) {
    DARK_MODE("Dark Mode"),
    LIGHT_MODE("Light Mode"),
    MATERIAL_THEME("Material You"),
    DEVICE_THEME("Follow System")
}