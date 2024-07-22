package com.tearas.expanemanagementjc.presentation.add_edit_expense.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.tearas.expanemanagementjc.R
import com.tearas.expanemanagementjc.presentation.common.TabIndicator
import com.tearas.expanemanagementjc.ui.theme.LightBlue
import com.tearas.expanemanagementjc.ui.theme.dynamicTopAppBarColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TopAddExpense(
    pagerState: PagerState,
    isShowDone: Boolean = false,
    onNavClick: () -> Unit,
    onSettingClick: () -> Unit,
    onDoneClick: () -> Unit
) {
    val coroutine = rememberCoroutineScope()
    val context = LocalContext.current
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = dynamicTopAppBarColor(),
                navigationIconContentColor = Color.White
            ),
            title = {
                Text(
                    style = MaterialTheme.typography.titleLarge,
                    text = context.getString(R.string.add_expense),
                    color = Color.White
                )
            }, navigationIcon = {
                IconButton(onClick = { onNavClick() }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                }
            },
            actions = {
                IconButton(onClick = { onSettingClick() }) {
                    Icon(
                        imageVector = Icons.Filled.Settings,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
                if (isShowDone) IconButton(onClick = { onDoneClick() }) {
                    Icon(
                        imageVector = Icons.Filled.Done,
                        contentDescription = null,
                        tint = Color.White
                    )
                }

            })

        TabIndicator(
            modifier = Modifier
                ,
            listTabs = listOf(
                TabItem(context.getString(R.string.expense), 0),
                TabItem(context.getString(R.string.income), 1)
            ), tabSelected = pagerState.currentPage, onTabSelected = {
                coroutine.launch {
                    pagerState.animateScrollToPage(it)
                }
            }
        )
    }
}