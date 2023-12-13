package ch.timofey.grader.ui.components.atom

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (value: String) -> Unit,
    label: @Composable () -> Unit,
    isError: Boolean = false,
    minLines: Int = 2,
    supportingText: @Composable () -> Unit,
    maxLines: Int? = null,
    singleLine: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = label,
        isError = isError,
        minLines = maxLines ?: minLines,
        supportingText = supportingText,
        singleLine = singleLine,
        keyboardOptions = keyboardOptions
    )
}