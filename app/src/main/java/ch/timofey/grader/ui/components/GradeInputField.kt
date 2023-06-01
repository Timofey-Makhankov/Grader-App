package ch.timofey.grader.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import ch.timofey.grader.ui.theme.spacing
import ch.timofey.grader.ui.utils.RegexPatterns

@Composable
fun GradeInputField(
    modifier: Modifier = Modifier,
    onGradeChange: (value: Double) -> Unit,
    onWeightChange: (value: Double) -> Unit
) {
    val grade = remember { mutableStateOf(0.0) }
    val weight = remember { mutableStateOf(0.0) }
    Row(
        modifier = modifier
    ) {
        TextField(
            value = grade.value.toString(),
            onValueChange = {
                if (it.isEmpty() || it.matches(RegexPatterns.OnlyNumberRegex)) {
                    onGradeChange(it.toDouble())
                }
            },
            shape = MaterialTheme.shapes.medium,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

        )
        Spacer(modifier = modifier.width(MaterialTheme.spacing.medium))
        TextField(
            value = weight.value.toString(),
            onValueChange = {
                if (it.isEmpty() || it.matches(RegexPatterns.OnlyNumberRegex)) {
                    onWeightChange(it.toDouble())
                }
            },
            shape = MaterialTheme.shapes.medium,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun GradeInputFieldPreview() {
    GradeInputField(
        onGradeChange = {},
        onWeightChange = {}
    )
}
