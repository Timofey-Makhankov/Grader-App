package ch.timofey.grader.ui.theme.scheme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

object TropicalParadiseScheme {
    //val SunlitApricot100 = Color(0xffffffff)
    val SunlitApricot90 = Color(0xfffff8f2)
    val SunlitApricot80 = Color(0xfffff1e4)
    val SunlitApricot70 = Color(0xffffe9d7)
    val SunlitApricot60 = Color(0xffffe2c9)
    val SunlitApricot50 = Color(0xffffdbbc)
    val SunlitApricot40 = Color(0xffffd4ae)
    val SunlitApricot30 = Color(0xffffcca1)
    val SunlitApricot20 = Color(0xffffc593)
    val SunlitApricot10 = Color(0xffffbe86)

    //val LemonMeringue100 = Color(0xffffffff)
    val LemonMeringue90 = Color(0xfffffcec)
    val LemonMeringue80 = Color(0xfffff8d9)
    val LemonMeringue70 = Color(0xfffff5c7)
    val LemonMeringue60 = Color(0xfffff2b4)
    val LemonMeringue50 = Color(0xffffeea1)
    val LemonMeringue40 = Color(0xffffeb8e)
    val LemonMeringue30 = Color(0xffffe87c)
    val LemonMeringue20 = Color(0xffffe469)
    val LemonMeringue10 = Color(0xffffe156)

    //val PeachCream100 = Color(0xffffffff)
    val PeachCream90 = Color(0xfffffdfa)
    val PeachCream80 = Color(0xfffffaf4)
    val PeachCream70 = Color(0xfffff8ef)
    val PeachCream60 = Color(0xfffff5e9)
    val PeachCream50 = Color(0xfffff3e4)
    val PeachCream40 = Color(0xfffff0de)
    val PeachCream30 = Color(0xffffeed9)
    val PeachCream20 = Color(0xffffebd3)
    val PeachCream10 = Color(0xffffe9ce)

    //val BlushPetal100 = Color(0xffffffff)
    val BlushPetal90 = Color(0xfffff7f8)
    val BlushPetal80 = Color(0xffffeff1)
    val BlushPetal70 = Color(0xffffe6eb)
    val BlushPetal60 = Color(0xffffdee4)
    val BlushPetal50 = Color(0xffffd6dd)
    val BlushPetal40 = Color(0xffffced6)
    val BlushPetal30 = Color(0xffffc5d0)
    val BlushPetal20 = Color(0xffffbdc9)
    val BlushPetal10 = Color(0xffffb5c2)

    //val AzureDream100 = Color(0xffffffff)
    val AzureDream90 = Color(0xffe9f0ff)
    val AzureDream80 = Color(0xffd3e1ff)
    val AzureDream70 = Color(0xffbcd2ff)
    val AzureDream60 = Color(0xffa6c3ff)
    val AzureDream50 = Color(0xff90b3ff)
    val AzureDream40 = Color(0xff7aa4ff)
    val AzureDream30 = Color(0xff6395ff)
    val AzureDream20 = Color(0xff4d86ff)
    val AzureDream10 = Color(0xff3777ff)

    val lightScheme: ColorScheme = lightColorScheme(
        primary = AzureDream10,
        onPrimary = SunlitApricot80,
        primaryContainer = Color.Green,
        onPrimaryContainer = Color.Green,
        inversePrimary = Color.Green,

        secondary = BlushPetal50,
        onSecondary = Color.Magenta,
        secondaryContainer = SunlitApricot20,
        onSecondaryContainer = PeachCream90,

        tertiary = SunlitApricot40,
        onTertiary = Color.White,
        tertiaryContainer = Color.Magenta,
        onTertiaryContainer = Color.Magenta,

        error = Color.White,
        onError = Color.Magenta,
        errorContainer = Color.Red,
        onErrorContainer = Color.Magenta,

        background = PeachCream80,
        onBackground = AzureDream20,

        surface = AzureDream90,
        onSurface = AzureDream30,
        inverseSurface = Color.Magenta,
        inverseOnSurface = Color.Magenta,
        surfaceVariant = AzureDream70,
        onSurfaceVariant = AzureDream10,

        outline = AzureDream30
    )
}