package ch.timofey.grader.ui.theme.scheme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import ch.timofey.grader.ui.theme.DarkGreen10
import ch.timofey.grader.ui.theme.DarkGreen20
import ch.timofey.grader.ui.theme.DarkGreen30
import ch.timofey.grader.ui.theme.DarkGreen40
import ch.timofey.grader.ui.theme.DarkGreen80
import ch.timofey.grader.ui.theme.DarkGreen90
import ch.timofey.grader.ui.theme.Green10
import ch.timofey.grader.ui.theme.Green20
import ch.timofey.grader.ui.theme.Green30
import ch.timofey.grader.ui.theme.Green40
import ch.timofey.grader.ui.theme.Green80
import ch.timofey.grader.ui.theme.Green90
import ch.timofey.grader.ui.theme.GreenGrey30
import ch.timofey.grader.ui.theme.GreenGrey50
import ch.timofey.grader.ui.theme.GreenGrey80
import ch.timofey.grader.ui.theme.GreenGrey90
import ch.timofey.grader.ui.theme.Grey10
import ch.timofey.grader.ui.theme.Grey20
import ch.timofey.grader.ui.theme.Grey90
import ch.timofey.grader.ui.theme.Grey95
import ch.timofey.grader.ui.theme.Grey99
import ch.timofey.grader.ui.theme.Red10
import ch.timofey.grader.ui.theme.Red20
import ch.timofey.grader.ui.theme.Red30
import ch.timofey.grader.ui.theme.Red40
import ch.timofey.grader.ui.theme.Red80
import ch.timofey.grader.ui.theme.Red90
import ch.timofey.grader.ui.theme.Violet10
import ch.timofey.grader.ui.theme.Violet20
import ch.timofey.grader.ui.theme.Violet30
import ch.timofey.grader.ui.theme.Violet40
import ch.timofey.grader.ui.theme.Violet80
import ch.timofey.grader.ui.theme.Violet90

object ColorSchemas {
    val darkColorSchema: ColorScheme = darkColorScheme(
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
    val lightColorSchema: ColorScheme = lightColorScheme(
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