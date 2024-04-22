package ch.timofey.grader.ui.theme.scheme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

object GraderColorScheme {
    //val red100 = Color(0xffffffff)
    private val red90 = Color(0xffFFEFF1)
    private val red80 = Color(0xffFFE0E3)
    val red70 = Color(0xffFFD0D5)
    val red60 = Color(0xffFFC0C7)
    val red50 = Color(0xffFFB1B8)
    val red40 = Color(0xffFFC0C7)
    private val red30 = Color(0xffFFA1AA)
    private val red20 = Color(0xffFF919C)
    val red10 = Color(0xffFF828E)
    val red0 = Color(0xffFF7280)

    //val green100 = Color(0xffffffff)
    private val green90 = Color(0xffEFFFF5)
    private val green80 = Color(0xffDFFFEC)
    val green70 = Color(0xffCFFFE2)
    val green60 = Color(0xffBFFFD8)
    val green50 = Color(0xffAEFFCF)
    private val green40 = Color(0xff9EFFC5)
    private val green30 = Color(0xff9EFFC5)
    private val green20 = Color(0xff8EFFBB)
    val green10 = Color(0xff7EFFB2)
    val green0 = Color(0xff6EFFA8)

    //yellow100 = Color(0xffffffff)
    private val yellow90 = Color(0xffFFFEEF)
    private val yellow80 = Color(0xffFFFEE0)
    val yellow70 = Color(0xffFFFDD0)
    val yellow60 = Color(0xffFFFDD0)
    val yellow50 = Color(0xffFFFDC0)
    val yellow40 = Color(0xffFFFCB1)
    private val yellow30 = Color(0xffFFFCA1)
    private val yellow20 = Color(0xffFFFB91)
    val yellow10 = Color(0xffFFFB82)
    val yellow0 = Color(0xffFFFA72)

    val grey100 = Color(0xffFAFAF5)
    private val grey90 = Color(0xffE3E3E3)
    private val grey80 = Color(0xffCBCBCB)
    val grey70 = Color(0xffB4B4B4)
    val grey60 = Color(0xff9D9D9D)
    val grey50 = Color(0xff858585)
    val grey40 = Color(0xff6E6E6E)
    private val grey30 = Color(0xff575757)
    val grey20 = Color(0xff3F3F3F)
    private val grey10 = Color(0xff282828)

    val darkScheme = darkColorScheme(
        primary = grey90,
        onPrimary = green90,
        primaryContainer = green70,
        onPrimaryContainer = green10,
        inversePrimary = green50,

        secondary = red30,
        onSecondary = red90,
        secondaryContainer = red30,
        onSecondaryContainer = red90,

        tertiary = yellow10,
        onTertiary = yellow10,
        tertiaryContainer = yellow10,
        onTertiaryContainer = yellow10,

        error = Color.White,
        onError = yellow10,
        errorContainer = red10,
        onErrorContainer = yellow10,

        background = grey10,
        onBackground = Color.White,

        surface = yellow10,
        onSurface = grey90,
        inverseSurface = yellow10,
        inverseOnSurface = yellow10,
        surfaceVariant = grey30,
        onSurfaceVariant = grey80,

        outline = grey90
    )

    val lightScheme = lightColorScheme(
        primary = grey10,
        onPrimary = grey90,
        primaryContainer = Color.Blue,
        onPrimaryContainer = Color.Blue,
        inversePrimary = Color.Blue,

        secondary = grey80,
        onSecondary = Color.Blue,
        secondaryContainer = grey50,
        onSecondaryContainer = grey90,

        tertiary = yellow10,
        onTertiary = yellow10,
        tertiaryContainer = yellow10,
        onTertiaryContainer = yellow10,

        error = Color.White,
        onError = yellow10,
        errorContainer = red10,
        onErrorContainer = yellow10,

        background = Color.White,
        onBackground = grey40,

        surface = yellow10,
        onSurface = grey40,
        inverseSurface = yellow10,
        inverseOnSurface = yellow10,
        surfaceVariant = grey90,
        onSurfaceVariant = grey10,

        outline = grey70
    )
}