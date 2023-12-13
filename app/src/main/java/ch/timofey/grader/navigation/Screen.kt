package ch.timofey.grader.navigation

sealed class Screen(var route: String) {
    data object MainScreen : Screen("main_screen")
    data object CreateSchoolScreen : Screen("create_school_screen")
    data object DivisionScreen : Screen("division_screen")
    data object ModuleScreen : Screen("module_screen")
    data object CreateDivisionScreen : Screen("create_division_screen")
    data object CreateModuleScreen : Screen("create_module_screen")
    data object ExamScreen : Screen("exam_screen")
    data object CreateExamScreen : Screen("create_exam_screen")
    data object SettingsScreen : Screen("settings_screen")
    data object ShareScreen : Screen("share_screen")
    data object SchoolEditScreen : Screen("edit_school_screen")
    data object DivisionEditScreen : Screen("edit_division_screen")
    data object ModuleEditScreen : Screen("edit_module_screen")
    data object CalculatorScreen : Screen("calculator_screen")
    data object WalkthroughScreen : Screen("walkthrough_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
