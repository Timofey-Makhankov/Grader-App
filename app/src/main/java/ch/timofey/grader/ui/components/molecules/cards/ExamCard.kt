package ch.timofey.grader.ui.components.molecules.cards

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import ch.timofey.grader.db.domain.exam.Exam
import ch.timofey.grader.ui.theme.GraderTheme
import ch.timofey.grader.ui.theme.getGradeColors
import ch.timofey.grader.ui.theme.spacing
//import io.github.serpro69.kfaker.Faker
//import io.github.serpro69.kfaker.fakerConfig
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.UUID

@Composable
fun ExamCard(
    modifier: Modifier = Modifier,
    exam: Exam,
    isOpen: Boolean = false,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onCheckBoxClick: () -> Unit
) {
    val checkedState = remember { mutableStateOf(exam.isSelected) }
    val expanded = remember { mutableStateOf(isOpen) }
    val interactionSource = remember { MutableInteractionSource() }
    Card(modifier = Modifier
        .animateContentSize(
            animationSpec = tween(
                durationMillis = 300, easing = LinearOutSlowInEasing
            )
        )
        .clickable(interactionSource = interactionSource, indication = null) {
            expanded.value = !expanded.value
        }
        .then(modifier), colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    ), shape = MaterialTheme.shapes.large) {
        Column(
            modifier = Modifier.padding(MaterialTheme.spacing.small)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.weight(0.9f),
                    text = exam.name,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Checkbox(
                    modifier = Modifier.align(Alignment.Top),
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
                text = exam.description ?: "",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = if (expanded.value) 4 else 2,
                overflow = TextOverflow.Ellipsis
            )
            AnimatedVisibility(
                visible = expanded.value
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = MaterialTheme.spacing.small),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    text = "Exam taken: ${
                        exam.date.format(
                            DateTimeFormatter.ofLocalizedDate(
                                FormatStyle.SHORT
                            )
                        )
                    }"
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = MaterialTheme.spacing.small),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(style = MaterialTheme.typography.labelLarge,
                    text = if (exam.grade == 0.0) AnnotatedString("") else buildAnnotatedString {
                        withStyle(
                            SpanStyle(
                                fontStyle = FontStyle.Italic
                            )
                        ) {
                            append("Average Grade: ")
                        }
                        withStyle(
                            SpanStyle(
                                color = getGradeColors(exam.grade), fontWeight = FontWeight.Bold
                            )
                        ) {
                            append("${exam.grade}")
                        }
                    })
                Text(
                    style = MaterialTheme.typography.labelLarge, text = "Weight: ${exam.weight}"
                )
            }
            AnimatedVisibility(visible = expanded.value) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.BottomEnd) {
                    Row {
                        IconButton(onClick = onEditClick) {
                            Icon(
                                imageVector = Icons.Default.Create,
                                contentDescription = "Edit the School Card"
                            )
                        }
                        IconButton(onClick = onDeleteClick) {
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
private fun ExamCardPreview() {
    //val f = Faker(fakerConfig { locale = "de_CH" })
    GraderTheme {
        ExamCard(exam = Exam(
            id = UUID.randomUUID(),
            name = "",//f.educator.schoolName(),
            description = LoremIpsum(20).values.joinToString(""),
            grade = 1.0,
            weight = 1.0,
            date = LocalDate.now(),
            moduleId = UUID.randomUUID()
        ), onCheckBoxClick = {}, onDeleteClick = {}, onEditClick = {})
    }
}

@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ExamCardDarkModePreview() {
    //val f = Faker(fakerConfig { locale = "de_CH" })
    GraderTheme {
        ExamCard(exam = Exam(
            id = UUID.randomUUID(),
            name = "",//f.educator.schoolName(),
            description = LoremIpsum(20).values.joinToString(""),
            grade = 5.9,
            weight = 1.0,
            date = LocalDate.of(2023, 6, 20),
            moduleId = UUID.randomUUID()
        ), onCheckBoxClick = {}, isOpen = true, onDeleteClick = {}, onEditClick = {})
    }
}