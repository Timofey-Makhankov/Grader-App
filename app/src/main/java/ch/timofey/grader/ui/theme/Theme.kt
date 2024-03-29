package ch.timofey.grader.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import ch.timofey.grader.db.AppTheme

private val darkColorPalette = ColorSchemas.darkColorSchema
private val lightColorPalette = ColorSchemas.lightColorSchema

@Composable
fun getColorschemeFromAppSetting(theme: AppTheme): ColorScheme {
    val useDynamicColors = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    return when {
        theme == AppTheme.MATERIAL_THEME && isSystemInDarkTheme() && useDynamicColors -> dynamicDarkColorScheme(
            LocalContext.current
        )

        theme == AppTheme.MATERIAL_THEME && !isSystemInDarkTheme() && useDynamicColors -> dynamicLightColorScheme(
            LocalContext.current
        )

        theme == AppTheme.DARK_MODE -> darkColorPalette
        else -> lightColorPalette
    }
}

@Composable
fun getColorScheme(): ColorScheme {
    val useDynamicColors = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    return when {
        useDynamicColors && isSystemInDarkTheme() -> dynamicDarkColorScheme(LocalContext.current)
        useDynamicColors && !isSystemInDarkTheme() -> dynamicLightColorScheme(LocalContext.current)
        isSystemInDarkTheme() -> darkColorPalette
        else -> lightColorPalette
    }
}

@Composable
fun GraderTheme(
    themeSetting: AppTheme? = null,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val useDynamicColors = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    val colors = when {
        themeSetting == null || themeSetting == AppTheme.DEVICE_THEME && useDynamicColors -> getColorScheme()
        else -> getColorschemeFromAppSetting(theme = themeSetting)
    }

    val gradeColors = when {
        darkTheme -> colorGradeDarkPalette
        else -> colorGradeLightPalette
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Color.Transparent.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    CompositionLocalProvider(
        LocalSpacing provides Spacing(),
        LocalColorGradePalette provides gradeColors
    ) {
        MaterialTheme(
            colorScheme = colors,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}