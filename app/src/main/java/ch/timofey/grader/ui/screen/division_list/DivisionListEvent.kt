package ch.timofey.grader.ui.screen.division_list

sealed class DivisionListEvent {
    object OnReturnBack : DivisionListEvent()
    object OnCreateDivision : DivisionListEvent()
}
