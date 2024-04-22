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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import ch.timofey.grader.db.domain.school.School
import ch.timofey.grader.ui.theme.GraderTheme
import ch.timofey.grader.ui.theme.getGradeColors
import ch.timofey.grader.ui.theme.spacing
import java.util.UUID

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SchoolCard(
    modifier: Modifier = Modifier,
    onCheckBoxClick: () -> Unit,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    colorGrade: Boolean = false,
    isOpen: Boolean = false,
    school: School
) {
    val checkedState = remember { mutableStateOf(school.isSelected) }
    val mutableInteractionSource = remember { MutableInteractionSource() }
    Card(
        modifier = Modifier
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300, easing = LinearOutSlowInEasing
                )
            )
            .combinedClickable(interactionSource = mutableInteractionSource,
                indication = null,
                onClick = { onClick() },
                onLongClick = { onLongClick() })
            .then(modifier),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        shape = MaterialTheme.shapes.large
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
                    text = school.name,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 2,
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
                softWrap = true,
                text = school.description ?: "",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = if (isOpen) 4 else 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
            if (school.grade != 0.0) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = buildAnnotatedString {
                        withStyle(
                            SpanStyle(
                                fontStyle = FontStyle.Italic
                            )
                        ) {
                            append("Average Grade: ")
                        }
                        if (colorGrade) {
                            pushStyle(
                                SpanStyle(
                                    color = getGradeColors(school.grade),
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                        append("${school.grade}")
                    },
                    style = MaterialTheme.typography.labelLarge,
                    textAlign = TextAlign.Start
                )
            }
            AnimatedVisibility(
                visible = isOpen, label = "Extending Button"
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.animateContentSize()) {
                        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                        Text(
                            text = school.address, style = MaterialTheme.typography.labelLarge
                        )
                        Text(
                            text = "${school.zipCode}, ${school.city}",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
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
private fun PreviewSchoolCard() {
    GraderTheme {
        SchoolCard(school = School(
            UUID.randomUUID(),
            "Title",//f.airport.europeanUnion.large(),
            "",//LoremIpsum(20).values.joinToString(""),
            "",
            "",
            "",
            isSelected = true,
            grade = 5.6
        ),
            onCheckBoxClick = {},
            onLongClick = {},
            onEditClick = {},
            onDeleteClick = {},
            colorGrade = true,
            onClick = {}
        )
    }
}

@Preview(
    showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun PreviewSchoolCardDarkMode() {
    //val f = Faker(fakerConfig { locale = "de_CH" })
    GraderTheme {
        SchoolCard(isOpen = true, school = School(
            UUID.randomUUID(),
            "Title",//f.airport.europeanUnion.large(),
            LoremIpsum(20).values.joinToString(""),
            "",//f.address.streetAddress(),
            "",//f.address.postcode(),
            "",//f.address.city(),
            grade = 3.0
        ),
            onCheckBoxClick = {},
            onLongClick = {},
            onEditClick = {},
            onDeleteClick = {},
            onClick = {}
        )
    }
}