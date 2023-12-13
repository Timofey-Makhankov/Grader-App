package ch.timofey.grader.ui.components.atom

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import ch.timofey.grader.ui.theme.GraderTheme
import ch.timofey.grader.ui.theme.spacing

@Composable
fun GradeInputField(
    modifier: Modifier = Modifier,
    onGradeChange: (value: String) -> Unit,
    onWeightChange: (value: String) -> Unit,
    grade: String,
    weight: String
) {
    val localGrade = remember { mutableStateOf(grade) }
    val localWeight = remember { mutableStateOf(weight) }
    Row(
        modifier = modifier
    ) {
        OutlinedTextField(
            value = localGrade.value,
            onValueChange = {
                localGrade.value = it
                onGradeChange(it)
            },
            shape = MaterialTheme.shapes.medium,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

        )
        Spacer(modifier = modifier.width(MaterialTheme.spacing.medium))
        OutlinedTextField(
            value = localWeight.value,
            onValueChange = {
                localWeight.value = it
                onWeightChange(it)
            },
            shape = MaterialTheme.shapes.medium,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun GradeInputFieldPreview() {
    GraderTheme {
        GradeInputField(onGradeChange = {}, onWeightChange = {}, grade = "4.5", weight = "1.0")
    }
}
