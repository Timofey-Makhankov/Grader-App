package ch.timofey.grader.ui.theme

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview

//val ColorScheme.warning: Color @Composable
//    get() = if (isSystemInDarkTheme()) Orange80 else Orange20

@Immutable
data class ColorGradePalette(
    val minimumGrade: Color = Color.Unspecified,
    val overGrade: Color = Color.Unspecified,
    val lowerGrade: Color = Color.Unspecified
)

val colorGradeLightPalette = ColorGradePalette(
    minimumGrade = Yellow30,
    overGrade = Green40,
    lowerGrade = Red40
)

val colorGradeDarkPalette = ColorGradePalette(
    minimumGrade = Yellow80,
    overGrade = Green80,
    lowerGrade = Red60
)

val LocalColorGradePalette = staticCompositionLocalOf { ColorGradePalette() }

val ColorScheme.gradeColors: ColorGradePalette
    @Composable
    @ReadOnlyComposable
    get() = LocalColorGradePalette.current

@Composable
fun getGradeColors(grade: Double): Color {
    return when {
        grade == 4.0 -> MaterialTheme.colorScheme.gradeColors.minimumGrade
        grade > 4.0 -> MaterialTheme.colorScheme.gradeColors.overGrade
        grade < 4.0 -> MaterialTheme.colorScheme.gradeColors.lowerGrade
        else -> Color.Unspecified
    }
}
@Preview
@Composable
private fun PreviewGradeColorsLight() {
    GraderTheme {
        Box(
            modifier = Modifier.background(MaterialTheme.colorScheme.background).padding(MaterialTheme.spacing.small)
        ) {
            Column {
                Text(text = buildAnnotatedString {
                    append("Grade: ")
                    withStyle(
                        SpanStyle(
                            color = MaterialTheme.colorScheme.gradeColors.overGrade
                        )
                    ) {
                        append("5.6")
                    }
                }, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onBackground)
                Text(text = buildAnnotatedString {
                    append("Grade: ")
                    withStyle(
                        SpanStyle(
                            color = MaterialTheme.colorScheme.gradeColors.minimumGrade
                        )
                    ) {
                        append("4")
                    }
                }, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onBackground)
                Text(text = buildAnnotatedString {
                    append("Grade: ")
                    withStyle(
                        SpanStyle(
                            color = MaterialTheme.colorScheme.gradeColors.lowerGrade
                        )
                    ) {
                        append("3.5")
                    }
                }, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onBackground)
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewGradeColorsDark() {
    GraderTheme {
        Box(
            modifier = Modifier.background(MaterialTheme.colorScheme.background).padding(MaterialTheme.spacing.small)
        ) {
            Column {
                Text(text = buildAnnotatedString {
                    append("Grade: ")
                    withStyle(
                        SpanStyle(
                            color = MaterialTheme.colorScheme.gradeColors.overGrade
                        )
                    ) {
                        append("5.6")
                    }
                }, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onBackground)
                Text(text = buildAnnotatedString {
                    append("Grade: ")
                    withStyle(
                        SpanStyle(
                            color = MaterialTheme.colorScheme.gradeColors.minimumGrade
                        )
                    ) {
                        append("4")
                    }
                }, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onBackground)
                Text(text = buildAnnotatedString {
                    append("Grade: ")
                    withStyle(
                        SpanStyle(
                            color = MaterialTheme.colorScheme.gradeColors.lowerGrade
                        )
                    ) {
                        append("3.5")
                    }
                }, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onBackground)
            }
        }
    }
}
