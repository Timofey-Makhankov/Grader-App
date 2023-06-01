package ch.timofey.grader.navigation

sealed class Screen(var route: String) {
    object MainScreen : Screen("main_screen")
    object CreateSchoolScreen : Screen("create_school_screen")
    object SemesterScreen : Screen("semester_screen")
    object ModuleScreen : Screen("module_screen")
    object ExamScreen : Screen("exam_screen")
    object SettingsScreen : Screen("settings_screen")
    object ShareScreen : Screen ("share_screen")
    object CalculatorScreen : Screen ("calculator_screen")
}
