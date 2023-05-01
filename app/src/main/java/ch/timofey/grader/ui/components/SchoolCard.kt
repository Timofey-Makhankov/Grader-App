package ch.timofey.grader.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Checkbox
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ch.timofey.grader.theme.GraderTheme
import ch.timofey.grader.theme.backgroundOffWhite
import ch.timofey.grader.theme.spacing
import ch.timofey.grader.theme.textHidden
import ch.timofey.grader.theme.textMain

@Composable
fun SchoolCard(
    modifier: Modifier = Modifier,
    isChecked: Boolean,
    title: String,
    description: String? = "",
    grade: Double
) {
    val checkedState = remember { mutableStateOf(isChecked) }
    Card(
        modifier = modifier, colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ), shape = MaterialTheme.shapes.large
    ) {
        Column(
            modifier = Modifier.padding(MaterialTheme.spacing.small)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title, style = MaterialTheme.typography.titleLarge
                )
                Checkbox(
                    checked = checkedState.value,
                    onCheckedChange = { checkedState.value = it },
                    colors = CheckboxDefaults.colors()
                )
            }
            Text(
                text = description ?: "", style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Average Grade: $grade",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.End
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
private fun PreviewSchoolCard() {
    GraderTheme {
        SchoolCard(
            isChecked = true,
            title = "Chemistry",
            description = "Exam About The Van-der-Wall Effect ajbvjhdkhjvjdhvjhd hhjbadsjuhv asdbvjuhb",
            grade = 6.0
        )
    }
}

@Preview(
    showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun PreviewSchoolCardDarkMode() {
    GraderTheme {
        SchoolCard(
            isChecked = false, title = "Mathematics", grade = 5.0
        )
    }
}