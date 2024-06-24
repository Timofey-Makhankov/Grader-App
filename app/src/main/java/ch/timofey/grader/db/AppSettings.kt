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
    val showNavigationIcons: Boolean = true,
    val colorGrades: Boolean = true,
    val swapNavigation: Boolean = false,
    val minimumGrade: Double = 4.0,
)
enum class AppTheme(val title: String) {
    GRADER_THEME_LIGHT("Grader Theme Light"),
    GRADER_THEME_DARK("Grader Theme Dark"),
    PINK_BLOSSOM("Pink Blossom"),
    SWAMP("Swamp"),
    DUSK_HORIZON_LIGHT("Dusk Horizon Light"),
    DUSK_HORIZON_DARK("Dusk Horizon Dark"),
    TROPICAL_PARADISE("Tropical Paradise"),
    DEVICE_THEME("Follow System")
}