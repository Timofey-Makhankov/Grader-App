package ch.timofey.grader.navigation

sealed class Screen(var route: String) {
    object MainScreen : Screen("main_screen")
    object CreateSchoolScreen : Screen("create_school_screen")
    object DivisionScreen : Screen("division_screen")
    object ModuleScreen : Screen("module_screen")
    object CreateDivisionScreen : Screen("create_division_screen")
    object CreateModuleScreen : Screen("create_module_screen")
    object ExamScreen : Screen("exam_screen")
    object CreateExamScreen : Screen("create_exam_screen")
    object SettingsScreen : Screen("settings_screen")
    object ShareScreen : Screen("share_screen")
    object CalculatorScreen : Screen("calculator_screen")
    object WalkthroughScreen : Screen("walkthrough_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
