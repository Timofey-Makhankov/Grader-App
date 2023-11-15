package ch.timofey.grader.ui.components.molecules.cards

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import ch.timofey.grader.db.domain.division.Division
import ch.timofey.grader.ui.theme.GraderTheme
import ch.timofey.grader.ui.theme.getGradeColors
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
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    containerColor: Color = MaterialTheme.colorScheme.surfaceVariant
) {
    val checkedState = remember { mutableStateOf(division.isSelected) }
    val expanded = remember { mutableStateOf(isOpen) }
    Card(
        modifier = Modifier
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300, easing = LinearOutSlowInEasing
                )
            )
            .combinedClickable(
                interactionSource = MutableInteractionSource(),
                indication = null,
                onClick = { expanded.value = !expanded.value },
                onLongClick = { onLongClick() },
            )
            .then(modifier), colors = CardDefaults.cardColors(
            containerColor = containerColor,
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
                    text = division.name,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Checkbox(
                    checked = checkedState.value, onCheckedChange = {
                        checkedState.value = it
                        onCheckBoxClick()
                    }, colors = CheckboxDefaults.colors()
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
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().padding(end = MaterialTheme.spacing.small)
            ) {
                Text(
                    style = MaterialTheme.typography.labelLarge,
                    text = if (division.grade == 0.0) AnnotatedString("") else buildAnnotatedString {
                        withStyle(
                            SpanStyle(
                                fontStyle = FontStyle.Italic
                            )
                        ) {
                            append("Average Grade: ")
                        }
                        withStyle(
                            SpanStyle(
                                color = getGradeColors(division.grade),
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append("${division.grade}")
                        }
                    }
                )
                Text(
                    text = "${division.schoolYear}",
                    style = MaterialTheme.typography.bodyMedium.plus(SpanStyle(fontWeight = FontWeight.Bold)),
                )
            }
            AnimatedVisibility(visible = expanded.value) {
                Row(
                    modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End
                ) {
                    Box {
                        Row {
                            IconButton(
                                onClick = onEditClick
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Create,
                                    contentDescription = "Edit the School Card"
                                )
                            }
                            IconButton(
                                onClick = onDeleteClick
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Edit the School Card"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
private fun DivisionCardPreview() {
    GraderTheme {
        DivisionCard(division = Division(
            id = UUID.randomUUID(),
            name = "Semester 1",
            description = LoremIpsum(20).values.joinToString(""),
            schoolYear = 2024,
            schoolId = UUID.randomUUID(),
            isSelected = false,
            grade = 0.0
        ), onLongClick = {}, onCheckBoxClick = {}, onDeleteClick = {}, onEditClick = {})
    }
}

@Preview(
    showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun DivisionCardDarkModePreview() {
    GraderTheme {
        DivisionCard(division = Division(
            id = UUID.randomUUID(),
            name = "Semester 3",
            description = LoremIpsum(20).values.joinToString(""),
            schoolYear = 2022,
            schoolId = UUID.randomUUID(),
            isSelected = true,
            grade = 4.0
        ),
            onLongClick = {},
            onCheckBoxClick = {},
            isOpen = true,
            onDeleteClick = {},
            onEditClick = {})
    }
}