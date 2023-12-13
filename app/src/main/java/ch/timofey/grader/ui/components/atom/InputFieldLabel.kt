package ch.timofey.grader.ui.components.atom

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp

@Composable
fun InputFieldLabel(
    message: String,
    required: Boolean = false
) {
    Text(
        text = buildAnnotatedString {
            append("$message ")
            if (required) {
                withStyle(
                    style = SpanStyle(
                        fontStyle = FontStyle.Italic, fontSize = 8.sp
                    )
                ) {
                    append("(Required)")
                }
            }
        }
    )
}