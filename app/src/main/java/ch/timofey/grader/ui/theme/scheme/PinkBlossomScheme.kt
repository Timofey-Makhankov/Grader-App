package ch.timofey.grader.ui.theme.scheme

import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

object PinkBlossomScheme {
    //val petalblush100 = Color(0xffFFFFFF)
    val petalblush90 = Color(0xffFFFCFD)
    val petalblush80 = Color(0xffFFF9FB)
    val petalblush70 = Color(0xffFFF6F9)
    val petalblush60 = Color(0xffFFF3F7)
    val petalblush50 = Color(0xffFFF1F4)
    val petalblush40 = Color(0xffFFEEF2)
    val petalblush30 = Color(0xffFFEBF0)
    val petalblush20 = Color(0xffFFE8EE)
    val petalblush10 = Color(0xffFFE5EC)

    //val rosyglow100 = Color(0xffFFFFFF)
    val rosyglow90 = Color(0xffFFF8FA)
    val rosyglow80 = Color(0xffFFF1F5)
    val rosyglow70 = Color(0xffFFEBF0)
    val rosyglow60 = Color(0xffFFE4EB)
    val rosyglow50 = Color(0xffFFDDE5)
    val rosyglow40 = Color(0xffFFD6E0)
    val rosyglow30 = Color(0xffFFD0DB)
    val rosyglow20 = Color(0xffFFC9D6)
    val rosyglow10 = Color(0xffFFC2D1)

    //val cottoncandy100 = Color(0xffFFFFFF)
    val cottoncandy90 = Color(0xffFFF7F9)
    val cottoncandy80 = Color(0xffFFEEF2)
    val cottoncandy70 = Color(0xffFFE6EC)
    val cottoncandy60 = Color(0xffFFDDE6)
    val cottoncandy50 = Color(0xffFFD5DF)
    val cottoncandy40 = Color(0xffFFCCD9)
    val cottoncandy30 = Color(0xffFFC4D3)
    val cottoncandy20 = Color(0xffFFBBCC)
    val cottoncandy10 = Color(0xffFFB3C6)

    //val berrybloom100 = Color(0xffFFFFFF)
    val berrybloom90 = Color(0xffFFF3F6)
    val berrybloom80 = Color(0xffFFE6EC)
    val berrybloom70 = Color(0xffFFDAE3)
    val berrybloom60 = Color(0xffFFCDDA)
    val berrybloom50 = Color(0xffFFC1D0)
    val berrybloom40 = Color(0xffFFB4C7)
    val berrybloom30 = Color(0xffFFA8BE)
    val berrybloom20 = Color(0xffFF9BB4)
    val berrybloom10 = Color(0xffFF8FAB)

    //val raspberryrose100 = Color(0xffFFFFFF)
    val raspberryrose90 = Color(0xffFFEFF3)
    val raspberryrose80 = Color(0xffFEDFE7)
    val raspberryrose70 = Color(0xffFECFDB)
    val raspberryrose60 = Color(0xffFDBFCF)
    val raspberryrose50 = Color(0xffFDAFC2)
    val raspberryrose40 = Color(0xffFC9FB6)
    val raspberryrose30 = Color(0xffFC8FAA)
    val raspberryrose20 = Color(0xffFB7F9E)
    val raspberryrose10 = Color(0xffFB6F92)

    val lightScheme = lightColorScheme(
        primary = berrybloom20,
        onPrimary = berrybloom90,
        primaryContainer = raspberryrose20,
        onPrimaryContainer = cottoncandy90,
        inversePrimary = petalblush50,

        secondary = rosyglow10,
        onSecondary = rosyglow90,
        secondaryContainer = cottoncandy10,
        onSecondaryContainer = cottoncandy90,

        tertiary = berrybloom20,
        onTertiary = Color.White,
        tertiaryContainer = cottoncandy20,
        onTertiaryContainer = raspberryrose90,

        error = Color.White,
        onError = raspberryrose40,
        errorContainer = raspberryrose10,
        onErrorContainer = raspberryrose70,

        background = petalblush90,
        onBackground = berrybloom10,

        surface = raspberryrose90,
        onSurface = berrybloom10,
        inverseSurface = raspberryrose10,
        inverseOnSurface = petalblush90,
        surfaceVariant = petalblush10,
        onSurfaceVariant = raspberryrose10,

        outline = berrybloom20
    )
}