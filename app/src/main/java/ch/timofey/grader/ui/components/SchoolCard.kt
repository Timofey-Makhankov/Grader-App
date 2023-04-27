package ch.timofey.grader.ui.components

import androidx.compose.foundation.background
import androidx.compose.material.Checkbox
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ch.timofey.grader.ui.theme.backgroundOffWhite
import ch.timofey.grader.ui.theme.textHidden
import ch.timofey.grader.ui.theme.textMain

@Composable
fun SchoolCard(isChecked: Boolean) {
    val checkedState = remember { mutableStateOf(isChecked) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                shape = RoundedCornerShape(12.dp),
                color = backgroundOffWhite
            )
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Title",
                    fontSize = 24.sp,
                    color = textMain
                )
                Spacer(Modifier.weight(1f))
                Checkbox(
                    checked = checkedState.value,
                    onCheckedChange = null
                )
            }
            Text(
                text = "description",
                color = textHidden
            )
            Text(
                text = "Average Grade: 5",
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSchoolCard() {
    SchoolCard(isChecked = true)
}