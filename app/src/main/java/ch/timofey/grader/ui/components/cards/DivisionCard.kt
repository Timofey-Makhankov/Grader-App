package ch.timofey.grader.ui.components.cards

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import ch.timofey.grader.db.domain.division.Division
import ch.timofey.grader.ui.theme.GraderTheme
import ch.timofey.grader.ui.theme.spacing
import java.util.UUID

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DivisionCard(
    modifier: Modifier = Modifier,
    division: Division,
    isOpen: Boolean = false,
    onCheckBoxClick: () -> Unit,
    onLongClick: () -> Unit,
){
    val checkedState = remember { mutableStateOf(division.isSelected) }
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
                onLongClick = { onLongClick() },
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
                    text = division.name,
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
                text = division.description ?: "",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = if (expanded.value) 4 else 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
            AnimatedVisibility(visible = expanded.value){}
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = "${division.schoolYear}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Average Grade: ${division.grade}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
private fun DivisionCardPreview(){
    GraderTheme {
        DivisionCard(
            division = Division(
                id = UUID.randomUUID(),
                name = "Semester 1",
                description = "lorem Impsum",
                schoolYear = 2024,
                schoolId = UUID.randomUUID(),
                isSelected = false,
                grade = 0.0
            ),
            onLongClick = {},
            onCheckBoxClick = {}
        )
    }
}

@Preview(
    showBackground = false,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun DivisionCardDarkModePreview(){
    GraderTheme {
        DivisionCard(
            division = Division(
                id = UUID.randomUUID(),
                name = "Semester 3",
                description = "lorem Impsumlorem Impsumlorem Impsumlorem Impsumlorem Impsumlorem Impsumlorem Impsumlorem Impsum",
                schoolYear = 2022,
                schoolId = UUID.randomUUID(),
                isSelected = true,
                grade = 0.0
            ),
            onLongClick = {},
            onCheckBoxClick = {},
            isOpen = true
        )
    }
}