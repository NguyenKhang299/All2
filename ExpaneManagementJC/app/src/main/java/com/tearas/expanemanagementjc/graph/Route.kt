package com.tearas.expanemanagementjc.graph

sealed class AppRoute(val route: String) {
    data object StartAppGraph : AppRoute("startAppGraph")
    data object AddGraph : AppRoute("addGraph")
    data object MainGraph : AppRoute("mainGraph")
}

sealed class StartAppGraph(val route: String) {
    data object SplashScreen : StartAppGraph("splashScreen")
    data object IntroScreen : StartAppGraph("introScreen")
}

sealed class AddGraph(val route: String) {
    data object AddExpenseScreen : AddGraph("addExpenseScreen")
    data object SettingCategoryScreen : AddGraph("settingCategoryScreen")
    data object AddCategoryScreen : AddGraph("addCategoryScreen")
    data object EditCategoryScreen : AddGraph("editCategoryScreen")
    data object EditRemindScreen : AddGraph("editRemindScreen")
    data object AddRemindScreen : AddGraph("addRemindScreen")
    data object RemindScreen : AddGraph("remindScreen")
}

sealed class MainGraph(val route: String) {
    data object HomeScreen : MainGraph("homeScreen")
    data object GraphScreen : MainGraph("graphScreen")
    data object ReportScreen : MainGraph("reportScreen")
    data object SettingScreen : MainGraph("profileScreen")
    data object DetailsScreen : MainGraph("detailsScreen")
    data object PasswordScreen : MainGraph("passwordScreen")
}
