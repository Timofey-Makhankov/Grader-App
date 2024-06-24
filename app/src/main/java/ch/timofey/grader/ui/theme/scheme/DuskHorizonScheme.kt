package ch.timofey.grader.ui.theme.scheme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

object DuskHorizonScheme {
    //val MistyHorizon100 = Color(0xffFFFFFF
    val MistyHorizon90 = Color(0xfff7fafd)
    val MistyHorizon80 = Color(0xfff0f5fa)
    val MistyHorizon70 = Color(0xffe8f0f8)
    val MistyHorizon60 = Color(0xffe1ebf6)
    val MistyHorizon50 = Color(0xffd9e5f3)
    val MistyHorizon40 = Color(0xffd2e0f1)
    val MistyHorizon30 = Color(0xffcadbef)
    val MistyHorizon20 = Color(0xffc3d6ec)
    val MistyHorizon10 = Color(0xffbbd1ea)

    //val SereneSky100 = Color(0xffFFFFFF)
    val SereneSky90 = Color(0xfff5f9fd)
    val SereneSky80 = Color(0xffeaf2fa)
    val SereneSky70 = Color(0xffe0ecf8)
    val SereneSky60 = Color(0xffd5e6f6)
    val SereneSky50 = Color(0xffcbdff3)
    val SereneSky40 = Color(0xffc0d9f1)
    val SereneSky30 = Color(0xffb6d3ef)
    val SereneSky20 = Color(0xffabccec)
    val SereneSky10 = Color(0xffa1c6ea)

    //val OceanicBlue100 = Color(0xffFFFFFF)
    val OceanicBlue90 = Color(0xffecf1f8)
    val OceanicBlue80 = Color(0xffd8e2f0)
    val OceanicBlue70 = Color(0xffC5D4E9)
    val OceanicBlue60 = Color(0xffB1C5E1)
    val OceanicBlue50 = Color(0xff9EB7DA)
    val OceanicBlue40 = Color(0xff8AA8D2)
    val OceanicBlue30 = Color(0xff779ACB)
    val OceanicBlue20 = Color(0xff638BC3)
    val OceanicBlue10 = Color(0xff507DBC)

    val MidnightPlum100 = Color(0xffFFFFFF)
    val MidnightPlum90 = Color(0xffE4E3E8)
    val MidnightPlum80 = Color(0xffC9C8D1)
    val MidnightPlum70 = Color(0xffAEACBA)
    val MidnightPlum60 = Color(0xff9390A3)
    val MidnightPlum50 = Color(0xff79758C)
    val MidnightPlum40 = Color(0xff5E5975)
    val MidnightPlum30 = Color(0xff433D5E)
    val MidnightPlum20 = Color(0xff282247)
    val MidnightPlum10 = Color(0xff0D0630)

    val lightScheme: ColorScheme = lightColorScheme(
        primary = MistyHorizon10,
        onPrimary = Color.White,
        primaryContainer = Color.Red,
        onPrimaryContainer = Color.Red,
        inversePrimary = Color.Red,

        secondary = SereneSky10,
        onSecondary = SereneSky90,
        secondaryContainer = SereneSky10,
        onSecondaryContainer = Color.White,

        tertiary = OceanicBlue40,
        onTertiary = OceanicBlue90,
        tertiaryContainer = Color.Red,
        onTertiaryContainer = Color.Red,

        error = Color.White,
        onError = OceanicBlue20,
        errorContainer = SereneSky10,
        onErrorContainer = Color.Red,

        background = MistyHorizon90,
        onBackground = SereneSky10,

        surface = Color.White,
        onSurface = OceanicBlue10,
        inverseSurface = Color.Red,
        inverseOnSurface = Color.Red,
        surfaceVariant = SereneSky70,
        onSurfaceVariant = SereneSky10,

        outline = SereneSky30,
    )
    val darkScheme: ColorScheme = darkColorScheme(
        primary = MistyHorizon90,
        onPrimary = OceanicBlue10,
        primaryContainer = Color.Green,
        onPrimaryContainer = Color.Green,
        inversePrimary = Color.Green,

        secondary = SereneSky10,
        onSecondary = Color.Green,
        secondaryContainer = SereneSky90,
        onSecondaryContainer = OceanicBlue20,

        tertiary = Color.Green,
        onTertiary = Color.Green,
        tertiaryContainer = Color.Green,
        onTertiaryContainer = Color.Green,

        error = Color.White,
        onError = Color.Green,
        errorContainer = OceanicBlue10,
        onErrorContainer = SereneSky90,

        background = MidnightPlum10,
        onBackground = Color.White,

        surface = OceanicBlue10,
        onSurface = Color.White,
        inverseSurface = Color.Green,
        inverseOnSurface = Color.Green,
        surfaceVariant = SereneSky30,
        onSurfaceVariant = Color.White,

        outline = SereneSky90,
    )
}