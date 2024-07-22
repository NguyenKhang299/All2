package com.tearas.expanemanagementjc.presentation.navigator

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tearas.expanemanagementjc.ExpenseApplication
import com.tearas.expanemanagementjc.graph.AddGraph
import com.tearas.expanemanagementjc.graph.MainGraph
import com.tearas.expanemanagementjc.graph.navigateToTab
import com.tearas.expanemanagementjc.presentation.details.DetailsScreen
import com.tearas.expanemanagementjc.presentation.details.DetailsViewModel
import com.tearas.expanemanagementjc.presentation.home.HomeEvent
import com.tearas.expanemanagementjc.presentation.home.HomeScreen
import com.tearas.expanemanagementjc.presentation.home.HomeViewModel
import com.tearas.expanemanagementjc.presentation.main.component.MyBottomAppBar
import com.tearas.expanemanagementjc.presentation.main.component.listItemsBottomHome
import com.tearas.expanemanagementjc.presentation.report.ReportScreen
import com.tearas.expanemanagementjc.presentation.report.ReportViewModel
import com.tearas.expanemanagementjc.presentation.setting.SettingScreen
import com.tearas.expanemanagementjc.presentation.setting.SettingViewModel
import com.tearas.expanemanagementjc.presentation.statistical.StatisticalScreen
import com.tearas.expanemanagementjc.presentation.statistical.StatisticalViewModel
import com.tearas.expanemanagementjc.ui.theme.ExpaneManagementJCTheme
import java.util.Calendar

fun NavBackStackEntry.isDestinationDetailsScreen(): Boolean {
    return destination.route?.startsWith(MainGraph.DetailsScreen.route) == true
}

fun NavBackStackEntry.isDestinationSettingScreen(): Boolean {
    return destination.route?.startsWith(MainGraph.SettingScreen.route) == true
}

@OptIn(ExperimentalSharedTransitionApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun MainNavigation(onDestination: (String) -> Unit) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    var showBottomApp by remember { mutableStateOf(false) }
    var isStartHomeFirst by remember { mutableStateOf(false) }

    LaunchedEffect(navBackStackEntry) {
        if (isStartHomeFirst) {
            val isShow =
                navBackStackEntry?.isDestinationDetailsScreen() == true || navBackStackEntry?.isDestinationSettingScreen() == true
            showBottomApp = isShow == false
        }
        isStartHomeFirst = true
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            Column {
                AnimatedVisibility(visible = showBottomApp) {
                    MyBottomAppBar(
                        list = listItemsBottomHome,
                        navBackStackEntry?.destination?.route
                    ) { id, destination ->
                        if (id == 2) {
                            onDestination(destination)
                            return@MyBottomAppBar
                        }
                        navigateToTab(
                            navController,
                            destination
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            SharedTransitionLayout() {
                NavHost(
                    navController = navController,
                    startDestination = MainGraph.HomeScreen.route,
                ) {
                    composable(route = MainGraph.HomeScreen.route) {
                        val viewModel: HomeViewModel = hiltViewModel()
                        LaunchedEffect(key1 = Unit) {
                            viewModel.initScreenData()
                        }
                        HomeScreen(
                            this@SharedTransitionLayout, this@composable,
                            viewModel,
                            viewModel.state.mapExpenses,
                            viewModel.state.totalProfitLoss
                        ) { itemId ->
                            navController.navigate("${MainGraph.DetailsScreen.route}/$itemId")
                        }
                    }
                    composable(route = MainGraph.GraphScreen.route) {
                        val viewModel: StatisticalViewModel = hiltViewModel()
                        StatisticalScreen(viewModel)
                    }
                    composable(route = MainGraph.ReportScreen.route) {
                        val viewModel: ReportViewModel = hiltViewModel()
                        ReportScreen(viewModel)
                    }
                    composable(route = MainGraph.SettingScreen.route) {
                        val viewModel: SettingViewModel = hiltViewModel()
                        SettingScreen(
                            viewModel,
                            { onDestination(it) },
                            { navController.popBackStack() }
                        )
                    }
                    composable(
                        route = "${MainGraph.DetailsScreen.route}/{itemId}",
                        arguments = listOf(
                            navArgument("itemId") { type = NavType.LongType }
                        )
                    ) { backStackEntry ->
                        val itemId = backStackEntry.arguments?.getLong("itemId")!!
                        val viewModel: DetailsViewModel = hiltViewModel()

                        DetailsScreen(this@SharedTransitionLayout, this@composable,
                            viewModel,
                            itemId,
                            onBack = {
                                navController.popBackStack()
                            }
                        ) {
                            onDestination(AddGraph.AddExpenseScreen.route + "/$it")
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun NavigatorMainPreview() {
    ExpaneManagementJCTheme {
        MainNavigation {}
    }
}
