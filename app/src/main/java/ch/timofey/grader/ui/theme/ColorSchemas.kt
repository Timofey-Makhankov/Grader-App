package ch.timofey.grader.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

object ColorSchemas {
    val darkColorPalette: ColorScheme = darkColorScheme(
        primary = Green80,
        onPrimary = Green20,
        primaryContainer = Green30,
        onPrimaryContainer = Green90,
        inversePrimary = Green40,
        secondary = DarkGreen80,
        onSecondary = DarkGreen20,
        secondaryContainer = DarkGreen30,
        onSecondaryContainer = DarkGreen90,
        tertiary = Violet80,
        onTertiary = Violet20,
        tertiaryContainer = Violet30,
        onTertiaryContainer = Violet90,
        error = Red80,
        onError = Red20,
        errorContainer = Red30,
        onErrorContainer = Red90,
        background = Grey10,
        onBackground = Grey90,
        surface = GreenGrey30,
        onSurface = GreenGrey80,
        inverseSurface = Grey90,
        inverseOnSurface = Grey10,
        surfaceVariant = GreenGrey30,
        onSurfaceVariant = GreenGrey80,
        outline = GreenGrey80
    )
    val lightColorPalette: ColorScheme = lightColorScheme(
        primary = Green40,
        onPrimary = Color.White,
        primaryContainer = Green90,
        onPrimaryContainer = Green10,
        inversePrimary = Green80,
        secondary = DarkGreen40,
        onSecondary = Color.White,
        secondaryContainer = DarkGreen90,
        onSecondaryContainer = DarkGreen10,
        tertiary = Violet40,
        onTertiary = Color.White,
        tertiaryContainer = Violet90,
        onTertiaryContainer = Violet10,
        error = Red40,
        onError = Color.White,
        errorContainer = Red90,
        onErrorContainer = Red10,
        background = Grey99,
        onBackground = Grey10,
        surface = GreenGrey90,
        onSurface = GreenGrey30,
        inverseSurface = Grey20,
        inverseOnSurface = Grey95,
        surfaceVariant = GreenGrey90,
        onSurfaceVariant = GreenGrey30,
        outline = GreenGrey50
    )
}