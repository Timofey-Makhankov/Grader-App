package ch.timofey.grader.ui.screen.calculator

sealed class CalculatorEvent {
    data class OnGradeChange(val id: Int, val grade: Double): CalculatorEvent()
    data class OnWeightChange(val id: Int, val weight: Double): CalculatorEvent()
    object OnFieldEmpty: CalculatorEvent()
}
