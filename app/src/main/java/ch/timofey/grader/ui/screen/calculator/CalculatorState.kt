package ch.timofey.grader.ui.screen.calculator

data class CalculatorState(
    val grades: List<String> = emptyList(),
    val weights: List<String> = emptyList(),
    val rowCount: Int = 3,
    val calculatedGrade: Double = 0.0,
    val showColoredGrade: Boolean = true,
    val calculatePoints: Boolean = false,
    val minimumGrade: Double = 4.0
)