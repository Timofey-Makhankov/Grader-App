package ch.timofey.grader.ui.components

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Checkbox
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import ch.timofey.grader.db.domain.school.School
import ch.timofey.grader.ui.theme.GraderTheme
import ch.timofey.grader.ui.theme.spacing
import java.util.UUID

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SchoolCard(
    modifier: Modifier = Modifier,
    onCheckBoxClick: () -> Unit,
    isOpen: Boolean = false,
    school: School,
    grade: Double
) {
    val checkedState = remember { mutableStateOf(school.isSelected) }
    val expanded = remember { mutableStateOf(isOpen) }
    Card(
        modifier = Modifier
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            )
            .combinedClickable(
                interactionSource = MutableInteractionSource(),
                indication = null,
                onClick = { expanded.value = !expanded.value },
                onLongClick = { println("Long Pressed") }
            )
            .then(modifier),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        shape = MaterialTheme.shapes.large
    ) {
        Column(
            modifier = Modifier
                .padding(MaterialTheme.spacing.small)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = school.name,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Checkbox(
                    checked = checkedState.value,
                    onCheckedChange = {
                        checkedState.value = it
                        onCheckBoxClick()
                    },
                    colors = CheckboxDefaults.colors()
                )
            }
            Text(
                modifier = Modifier.padding(end = MaterialTheme.spacing.extraSmall),
                text = school.description ?: "",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = if (expanded.value) 4 else 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
            AnimatedVisibility(visible = expanded.value) {
                Column(modifier = Modifier.animateContentSize()) {
                    Text(text = school.address)
                    Text(text = "${school.zipCode}, ${school.city}")
                }
            }
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
            school = School(
                UUID.randomUUID(),
                "Technische Berufsschule Zürich",
                "Eine Berufliche Schule, in der mann über technische Fächern Lernt. Diese Schule wird von Lernenden besucht",
                "",
                "",
                "",
                isSelected = true
            ),
            grade = 6.0,
            onCheckBoxClick = {}
        )
    }
}

@Preview(
    showBackground = false,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun PreviewSchoolCardDarkMode() {
    GraderTheme {
        SchoolCard(
            isOpen = true,
            school = School(
                UUID.randomUUID(),
                "Berufsmaturitätsschule Zürich",
                "Eine Berufliche Schule, in der mann über technische Fächern Lernt. Diese Schule wird von Lernenden besucht",
                "Bächlerstrasse 55",
                "8046",
                "Zürich"
            ),
            grade = 5.0,
            onCheckBoxClick = {}
        )
    }
}