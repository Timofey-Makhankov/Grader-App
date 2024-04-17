package ch.timofey.grader.db

import ch.timofey.grader.type.DateFormatting
import kotlinx.serialization.Serializable

@Serializable
data class AppSettings(
    val theme: AppTheme = AppTheme.DEVICE_THEME,
    val dateFormatter: DateFormatting = DateFormatting.DEFAULT,
    val calculatePoints: Boolean = false,
    val doublePoints: Boolean = false,
    val enableSwipeToDelete: Boolean = false,
    val showNavigationIcons: Boolean = false,
    val minimumGrade: Double = 4.0,
)
enum class AppTheme(val title: String) {
    //DARK_MODE("Dark Mode"),
    //LIGHT_MODE("Light Mode"),
    //MATERIAL_THEME("Material You"),
    GRADER_THEME_LIGHT("Grader Theme Light"),
    GRADER_THEME_DARK("Grader Theme Dark"),
    PINK_BLOSSOM("Pink Blossom Theme"),
    DEVICE_THEME("Follow System")
}