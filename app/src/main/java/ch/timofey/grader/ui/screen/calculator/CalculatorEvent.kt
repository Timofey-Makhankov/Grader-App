package ch.timofey.grader.ui.screen.calculator

sealed class CalculatorEvent {
    data class OnGradeChange(val index: Int, val grade: String) : CalculatorEvent()
    data class OnWeightChange(val index: Int, val weight: String) : CalculatorEvent()
    data object OnAddFieldClick : CalculatorEvent()
    data object OnRemoveFieldClick : CalculatorEvent()
}
