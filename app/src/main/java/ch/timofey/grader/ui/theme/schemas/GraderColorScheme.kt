package ch.timofey.grader.ui.theme.schemas

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
        primary = green80,
        onPrimary = green20,
        primaryContainer = green30,
        onPrimaryContainer = green90,
        inversePrimary = green40,
        secondary = red80,
        onSecondary = red20,
        secondaryContainer = red30,
        onSecondaryContainer = red90,
        tertiary = yellow80,
        onTertiary = yellow20,
        tertiaryContainer = yellow30,
        onTertiaryContainer = yellow90,
        error = red80,
        onError = red20,
        errorContainer = red30,
        onErrorContainer = red90,
        background = grey10,
        onBackground = grey90,
        surface = grey30,
        onSurface = grey80,
        inverseSurface = grey90,
        inverseOnSurface = grey10,
        surfaceVariant = grey30,
        onSurfaceVariant = grey80,
        outline = grey80
    )

    val lightScheme = lightColorScheme(
        primary = green10,
        onPrimary = green90,
        primaryContainer = green30,
        onPrimaryContainer = green90,
        inversePrimary = green40,
        secondary = red10,
        onSecondary = red90,
        secondaryContainer = red30,
        onSecondaryContainer = red90,
        tertiary = yellow10,
        onTertiary = yellow90,
        tertiaryContainer = yellow30,
        onTertiaryContainer = yellow90,
        error = red80,
        onError = red20,
        errorContainer = red30,
        onErrorContainer = red90,
        background = grey10,
        onBackground = grey90,
        surface = grey30,
        onSurface = grey80,
        inverseSurface = grey90,
        inverseOnSurface = grey10,
        surfaceVariant = grey30,
        onSurfaceVariant = grey80,
        outline = grey80
    )
}