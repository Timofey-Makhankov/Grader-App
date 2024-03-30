package ch.timofey.grader.ui.components.molecules.cards

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.sp
import ch.timofey.grader.db.domain.module.Module
import ch.timofey.grader.ui.theme.GraderTheme
import ch.timofey.grader.ui.theme.getGradeColors
import ch.timofey.grader.ui.theme.spacing
//import io.github.serpro69.kfaker.Faker
//import io.github.serpro69.kfaker.fakerConfig
import java.util.UUID

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ModuleCard(
    modifier: Modifier = Modifier,
    module: Module,
    onCheckBoxClick: () -> Unit,
    onLongClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    isOpen: Boolean = false,
) {
    val checkedState = remember { mutableStateOf(module.isSelected) }
    val mutableInteractionSource = remember { MutableInteractionSource() }
    val expanded = remember { mutableStateOf(isOpen) }
    Card(
        modifier = Modifier
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300, easing = LinearOutSlowInEasing
                )
            )
            .combinedClickable(interactionSource = mutableInteractionSource,
                indication = null,
                onClick = { expanded.value = !expanded.value },
                onLongClick = { onLongClick() })
            .then(modifier), colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
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
                    text = module.name,
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
            if (module.description != "") {
                Text(
                    modifier = Modifier.padding(end = MaterialTheme.spacing.extraSmall),
                    text = module.description ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = if (expanded.value) 4 else 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            AnimatedVisibility(visible = expanded.value) {
                Text(modifier = Modifier.padding(
                    //start = MaterialTheme.spacing.small,
                    top = MaterialTheme.spacing.small
                ), text = buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            fontStyle = FontStyle.Italic, fontSize = 14.sp
                        )
                    ) {
                        append("Instructor: ")
                    }
                    withStyle(
                        SpanStyle(
                            fontWeight = FontWeight.Medium
                        )
                    ) {
                        append("${module.teacherFirstname} ${module.teacherLastname}")
                    }
                })
            }
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
            if (module.grade != 0.0) {
                Text(style = MaterialTheme.typography.labelLarge,
                    text = buildAnnotatedString {
                        withStyle(
                            SpanStyle(
                                fontStyle = FontStyle.Italic
                            )
                        ) {
                            append("Average Grade: ")
                        }
                        withStyle(
                            SpanStyle(
                                color = getGradeColors(module.grade),
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append("${module.grade}")
                        }
                    })
            }
            AnimatedVisibility(visible = expanded.value) {
                Box(
                    modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.BottomEnd
                ) {
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

@Preview(showBackground = false)
@Composable
private fun ModuleCardPreview() {
    //val f = Faker(fakerConfig { locale = "de_CH" })
    GraderTheme {
        ModuleCard(module = Module(
            id = UUID.randomUUID(),
            name = "",//f.science.branch.formalBasic(),
            description = LoremIpsum().values.joinToString(""),
            isSelected = false,
            divisionId = UUID.randomUUID(),
            teacherFirstname = "",
            teacherLastname = "",
            grade = 4.0
        ), onCheckBoxClick = {}, onLongClick = {}, onDeleteClick = {}, onEditClick = {})
    }
}

@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ModuleCardDarkModePreview() {
    //val f = Faker(fakerConfig { locale = "de_CH" })
    GraderTheme {
        Column {
            ModuleCard(module = Module(
                id = UUID.randomUUID(),
                name = "",//f.science.branch.formalApplied(),
                description = LoremIpsum().values.joinToString(""),
                isSelected = true,
                divisionId = UUID.randomUUID(),
                teacherFirstname = "",//f.name.firstName(),
                teacherLastname = "",//f.name.lastName(),
                grade = 3.9
            ),
                isOpen = true,
                onCheckBoxClick = {},
                onLongClick = {},
                onDeleteClick = {},
                onEditClick = {})
            ModuleCard(module = Module(
                id = UUID.randomUUID(),
                name = "",//f.science.branch.formalApplied(),
                description = "",
                isSelected = true,
                divisionId = UUID.randomUUID(),
                teacherFirstname = "",//f.name.firstName(),
                teacherLastname = "",//f.name.lastName(),
                grade = 3.9
            ),
                isOpen = true,
                onCheckBoxClick = {},
                onLongClick = {},
                onDeleteClick = {},
                onEditClick = {})
        }
    }
}