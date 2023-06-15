package ch.timofey.grader.ui.screen.calculator

data class CalculatorState(
    var grade: List<Double> = emptyList(),
    var weight: List<Double> = emptyList(),
    var rowCount: Int = 1,
    var calculatedGrade: Double = 0.0
)