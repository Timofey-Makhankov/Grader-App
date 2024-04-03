package ch.timofey.grader.ui.components.atom

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import ch.timofey.grader.ui.theme.spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownMenu(
    value: String,
    title: String,
    items: @Composable (afterSelection: () -> Unit) -> Unit
) {
    val expanded = remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        modifier = Modifier.fillMaxWidth().padding(horizontal = MaterialTheme.spacing.medium),
        expanded = expanded.value,
        onExpandedChange = { expanded.value = !expanded.value }) {
        OutlinedTextField(
            value = value,
            onValueChange = {},
            label = { Text(text = title) },
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value) },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
            modifier = Modifier.menuAnchor().fillMaxWidth()
        )
        DropdownMenu(
            modifier = Modifier.exposedDropdownSize(),
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
        ) { items { expanded.value = false } }
    }
}