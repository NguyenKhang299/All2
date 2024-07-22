package com.tearas.expanemanagementjc.presentation.main.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tearas.expanemanagementjc.R
import com.tearas.expanemanagementjc.graph.AddGraph
import com.tearas.expanemanagementjc.graph.MainGraph
import com.tearas.expanemanagementjc.presentation.common.BottomNavItem
import com.tearas.expanemanagementjc.ui.theme.Blue
import com.tearas.expanemanagementjc.ui.theme.WhiteGray
import com.tearas.expanemanagementjc.ui.theme.isNightMode
import com.tearas.expanemanagementjc.ui.theme.md_theme_dark_background_appbar

val listItemsBottomHome = listOf(
    BottomNavItem(0, R.string.home, R.drawable.house, MainGraph.HomeScreen.route),
    BottomNavItem(
        1,
        R.string.graph,
        R.drawable.graph,
        MainGraph.GraphScreen.route
    ),
    BottomNavItem(
        2,
        -1,
        -1,
        AddGraph.AddExpenseScreen.route
    ),
    BottomNavItem(
        3,
        R.string.report,
        R.drawable.report,
        MainGraph.ReportScreen.route
    ),
    BottomNavItem(4, R.string.setting, R.drawable.setting, MainGraph.SettingScreen.route),
)

@Composable
fun MyBottomAppBar(
    list: List<BottomNavItem>,
    onItemSelected1: String?,
    onItemSelected: (Int, String) -> Unit
) {
    val context = LocalContext.current
    var rememberItemSelected by remember {
        mutableStateOf("")
    }
    if (onItemSelected1 != null) {
        rememberItemSelected = onItemSelected1
    }
    NavigationBar(
        modifier = Modifier
            .fillMaxWidth(),
        containerColor = if (isNightMode()) md_theme_dark_background_appbar else Color.White
    ) {
        list.forEachIndexed { index, bottomNavItem ->
            if (index == 2) {
                NavigationBarItem(
                    colors = NavigationBarItemDefaults.colors(

                    ),
                    selected = false,
                    onClick = { },
                    icon = {
                        FloatingActionButton(onClick = {
                            onItemSelected(index, bottomNavItem.destination)
                        }, containerColor = Blue) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                    })
            } else {
                NavigationBarItem(
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = if (isNightMode()) Color.Transparent else WhiteGray
                    ),
                    selected = bottomNavItem.destination == onItemSelected1,
                    onClick = {
                        onItemSelected(index, bottomNavItem.destination)
                        rememberItemSelected = bottomNavItem.destination
                    },
                    label = {
                        Text(
                            text = context.getString(bottomNavItem.title),
                            style = MaterialTheme.typography.labelSmall,
                            color = if (rememberItemSelected == bottomNavItem.destination) Blue else {
                                if (isNightMode()) Color.White else Color.Black
                            }
                        )
                    },
                    icon = {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(bottomNavItem.icon),
                            contentDescription = null,
                            tint = if (rememberItemSelected == bottomNavItem.destination) Blue else {
                                if (isNightMode()) Color.White else Color.Black
                            },
                        )
                    })
            }
        }
    }
}