package com.tearas.expanemanagementjc.presentation.graph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.tearas.expanemanagementjc.graph.AddGraph
import com.tearas.expanemanagementjc.graph.AppRoute
import com.tearas.expanemanagementjc.graph.MainGraph
import com.tearas.expanemanagementjc.graph.StartAppGraph
import com.tearas.expanemanagementjc.presentation.add_edit_category.AddEditCategoryEvent
import com.tearas.expanemanagementjc.presentation.add_edit_category.AddEditCategoryScreen
import com.tearas.expanemanagementjc.presentation.add_edit_category.AddEditCategoryViewModel
import com.tearas.expanemanagementjc.presentation.add_edit_expense.AddExpenseScreen
import com.tearas.expanemanagementjc.presentation.add_edit_expense.AddExpenseViewModel
import com.tearas.expanemanagementjc.presentation.add_edit_expense.EventAddExpense
import com.tearas.expanemanagementjc.presentation.intro.IntroScreen
import com.tearas.expanemanagementjc.presentation.navigator.MainNavigation
import com.tearas.expanemanagementjc.presentation.password.PasswordScreen
import com.tearas.expanemanagementjc.presentation.password.PasswordViewModel
import com.tearas.expanemanagementjc.presentation.password.component.PasscodeViewType
import com.tearas.expanemanagementjc.presentation.remind.RemindAddUpdateScreen
import com.tearas.expanemanagementjc.presentation.remind.RemindAddUpdateViewModel
import com.tearas.expanemanagementjc.presentation.remind.RemindScreen
import com.tearas.expanemanagementjc.presentation.remind.RemindViewModel
import com.tearas.expanemanagementjc.presentation.setting_category.SettingCateViewModel
import com.tearas.expanemanagementjc.presentation.setting_category.SettingCategoryScreen
import com.tearas.expanemanagementjc.presentation.splash.SplashScreen

@Composable
fun AppGraph() {
    val navController = rememberNavController()

    //    NavBackStackEntry cung cấp thông tin mục hiện tại trong ngăn sếp
    NavHost(
        navController = navController,
        startDestination = AppRoute.StartAppGraph.route
    ) {

        // StartAppGraph Navigation
        navigation(
            startDestination = StartAppGraph.SplashScreen.route,
            route = AppRoute.StartAppGraph.route
        ) {
            composable(route = StartAppGraph.SplashScreen.route) {
                SplashScreen { route ->
                    navController.navigate(route)
                }
            }
            composable(route = StartAppGraph.IntroScreen.route) {
                IntroScreen {
                    navController.navigate(AppRoute.MainGraph.route)
                }
            }
        }

        composable(route = AppRoute.MainGraph.route) {
            MainNavigation {
                navController.navigate(it)
            }
        }
        composable(route = MainGraph.PasswordScreen.route + "/{type}",
            arguments = listOf(
                navArgument("type") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val viewmodel: PasswordViewModel = hiltViewModel()
            val type = backStackEntry.arguments?.getString("type")
            viewmodel.setType(PasscodeViewType.valueOf(type!!))
            PasswordScreen(
                viewModel = viewmodel,
                onBack = { navController.popBackStack() },
                onNavigate = { route -> navController.navigate(route) })

        }
        composable(route = AddGraph.RemindScreen.route) {
            val viewModel: RemindViewModel = hiltViewModel()
            RemindScreen(
                viewModel,
                { navController.navigate(it) },
                { navController.popBackStack() }
            )
        }
        composable(route = AddGraph.AddRemindScreen.route) {
            val viewModel: RemindAddUpdateViewModel = hiltViewModel()
            RemindAddUpdateScreen(viewModel) {
                navController.popBackStack()
            }
        }
        composable(
            route = AddGraph.EditRemindScreen.route + "/{id}",
            arguments = listOf(navArgument("id") {
                type = NavType.LongType
            })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id")

            val viewModel: RemindAddUpdateViewModel = hiltViewModel()
            RemindAddUpdateScreen(viewModel, id) {
                navController.popBackStack()
            }
        }
        composable(
            route = AddGraph.AddExpenseScreen.route,
        ) { backStackEntry ->
            val viewModel: AddExpenseViewModel = hiltViewModel()
            AddExpenseScreen(viewModel = viewModel, isUpdate = false, onSettingClick = {
                navController.navigate(AddGraph.SettingCategoryScreen.route)
            }, onNavClick = { navController.popBackStack() })
        }
        composable(
            route = AddGraph.AddExpenseScreen.route + "/{idExpense}",
            arguments = listOf(navArgument("idExpense") {
                type = NavType.LongType
            })
        ) { backStackEntry ->
            val viewModel: AddExpenseViewModel = hiltViewModel()
            val idExpense = backStackEntry.arguments?.getLong("idExpense")

            val isUpdate = idExpense != null
            if (isUpdate) {
                viewModel.onEvent(EventAddExpense.GetExpense(idExpense!!))
            }
            AddExpenseScreen(viewModel = viewModel, isUpdate = isUpdate, onSettingClick = {
                navController.navigate(AddGraph.SettingCategoryScreen.route)
            }, onNavClick = { navController.popBackStack() })
        }

        // AddGraph Navigation
        navigation(
            startDestination = AddGraph.SettingCategoryScreen.route, route = AppRoute.AddGraph.route
        ) {

            composable(route = AddGraph.SettingCategoryScreen.route) {
                val viewModel: SettingCateViewModel = hiltViewModel()
                SettingCategoryScreen(viewModel = viewModel,
                    onBack = { navController.popBackStack() },
                    onDestination = { navController.navigate(it) })
            }
            composable(route = AddGraph.AddCategoryScreen.route) {
                val viewModel: AddEditCategoryViewModel = hiltViewModel()
                AddEditCategoryScreen(isAddCategory = true, viewModel) {
                    navController.popBackStack()
                }
            }
            composable(
                route = AddGraph.EditCategoryScreen.route + "/{id}",
                arguments = listOf(navArgument("id") {
                    type = NavType.LongType
                })
            ) { backStackEntry ->
                val viewModel: AddEditCategoryViewModel = hiltViewModel()
                val id = backStackEntry.arguments?.getLong("id")

                LaunchedEffect(Unit) {
                    id?.let { viewModel.onEvent(AddEditCategoryEvent.GetCategory(it)) }
                }
                AddEditCategoryScreen(isAddCategory = false, viewModel) {
                    navController.popBackStack()
                }
            }
        }
    }
}
