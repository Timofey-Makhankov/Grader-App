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

    val DeepSea100 = Color(0xffffffff)
    val DeepSea90 = Color(0xffe6ecf0)
    val DeepSea80 = Color(0xffcedae1)
    val DeepSea70 = Color(0xffb5c7d3)
    val DeepSea60 = Color(0xff9db4c4)
    val DeepSea50 = Color(0xff84a2b5)
    val DeepSea40 = Color(0xff6c8fa6)
    val DeepSea30 = Color(0xff537c98)
    val DeepSea20 = Color(0xff3b6a89)
    val DeepSea10 = Color(0xff22577a)

    val TurquoiseMist100 = Color(0xffffffff)
    val TurquoiseMist90 = Color(0xffe9f5f5)
    val TurquoiseMist80 = Color(0xffd3ebeb)
    val TurquoiseMist70 = Color(0xffbde0e1)
    val TurquoiseMist60 = Color(0xffa7d6d7)
    val TurquoiseMist50 = Color(0xff90cccd)
    val TurquoiseMist40 = Color(0xff7ac2c3)
    val TurquoiseMist30 = Color(0xff64b7b9)
    val TurquoiseMist20 = Color(0xff4eadaf)
    val TurquoiseMist10 = Color(0xff38a3a5)

    val MeadowGreen100 = Color(0xffffffff)
    val MeadowGreen90 = Color(0xffecf9f4)
    val MeadowGreen80 = Color(0xffdaf4e8)
    val MeadowGreen70 = Color(0xffc7eedd)
    val MeadowGreen60 = Color(0xffb4e8d2)
    val MeadowGreen50 = Color(0xffa2e3c6)
    val MeadowGreen40 = Color(0xff8fddbb)
    val MeadowGreen30 = Color(0xff7cd7b0)
    val MeadowGreen20 = Color(0xff6ad2a4)
    val MeadowGreen10 = Color(0xff57cc99)

    val MintyBreeze100 = Color(0xffffffff)
    val MintyBreeze90 = Color(0xfff1fdf4)
    val MintyBreeze80 = Color(0xffe3fbe8)
    val MintyBreeze70 = Color(0xffd5f9dd)
    val MintyBreeze60 = Color(0xffc7f7d2)
    val MintyBreeze50 = Color(0xffb8f5c6)
    val MintyBreeze40 = Color(0xffaaf3bb)
    val MintyBreeze30 = Color(0xff9cf1b0)
    val MintyBreeze20 = Color(0xff8eefa4)
    val MintyBreeze10 = Color(0xff80ed99)

    val lightScheme: ColorScheme = lightColorScheme(
        primary = TurquoiseMist80,
        onPrimary = TurquoiseMist100,
        primaryContainer = TurquoiseMist30,
        onPrimaryContainer = TurquoiseMist10,
        inversePrimary = TurquoiseMist40,

        secondary = MeadowGreen80,
        onSecondary = MeadowGreen100,
        secondaryContainer = MeadowGreen30,
        onSecondaryContainer = MeadowGreen10,

        tertiary = MintyBreeze80,
        onTertiary = MintyBreeze100,
        tertiaryContainer = MintyBreeze30,
        onTertiaryContainer = MintyBreeze10,

        error = DeepSea80,
        onError = DeepSea20,
        errorContainer = DeepSea30,
        onErrorContainer = DeepSea90,

        background = DeepSea90,
        onBackground = DeepSea10,

        surface = DeepSea100,
        onSurface = DeepSea10,
        inverseSurface = DeepSea10,
        inverseOnSurface = DeepSea90,
        surfaceVariant = DeepSea70,
        onSurfaceVariant = DeepSea20,

        outline = DeepSea30,

        )
    val darkScheme: ColorScheme = darkColorScheme(
        primary = MistyHorizon20,
        onPrimary = MistyHorizon10,
        primaryContainer = MistyHorizon70,
        onPrimaryContainer = Color.White,
        inversePrimary = MistyHorizon60,

        secondary = OceanicBlue20,
        onSecondary = OceanicBlue10,
        secondaryContainer = OceanicBlue70,
        onSecondaryContainer = Color.White,

        tertiary = MidnightPlum20,
        onTertiary = MidnightPlum10,
        tertiaryContainer = MidnightPlum70,
        onTertiaryContainer = MidnightPlum100,

        error = OceanicBlue20,
        onError = OceanicBlue80,
        errorContainer = OceanicBlue70,
        onErrorContainer = OceanicBlue10,

        background = MistyHorizon10,
        onBackground = MistyHorizon90,

        surface = SereneSky10,
        onSurface = Color.White,
        inverseSurface = SereneSky90,
        inverseOnSurface = SereneSky10,
        surfaceVariant = SereneSky30,
        onSurfaceVariant = SereneSky80,

        outline = SereneSky70,
    )
}