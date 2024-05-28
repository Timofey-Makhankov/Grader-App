package ch.timofey.grader.ui.theme.scheme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

object SwampScheme {
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
        primary = TurquoiseMist10,
        onPrimary = TurquoiseMist90,
        primaryContainer = Color.Green,
        onPrimaryContainer = Color.Green,
        inversePrimary = Color.Green,

        secondary = TurquoiseMist30,
        onSecondary = Color.Green,
        secondaryContainer = DeepSea10,
        onSecondaryContainer = DeepSea90,

        tertiary = Color.Red,
        onTertiary = Color.Red,
        tertiaryContainer = Color.Red,
        onTertiaryContainer = Color.Red,

        error = DeepSea90,
        onError = Color.Red,
        errorContainer = DeepSea10,
        onErrorContainer = Color.Red,

        background = MeadowGreen90,
        onBackground = DeepSea10,

        surface = MintyBreeze90,
        onSurface = DeepSea10,
        inverseSurface = Color.Red,
        inverseOnSurface = Color.Red,
        surfaceVariant = DeepSea70,
        onSurfaceVariant = DeepSea10,

        outline = DeepSea40,
    )
}