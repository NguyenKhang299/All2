package com.tearas.expanemanagementjc.presentation.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LifecycleResumeEffect
import com.tearas.expanemanagementjc.data.dto.ExpenseDto
import com.tearas.expanemanagementjc.data.mapper.calculateTotalProfitLoss
import com.tearas.expanemanagementjc.domain.model.ProfitLoss
import com.tearas.expanemanagementjc.presentation.common.AnimNotFound
import com.tearas.expanemanagementjc.presentation.home.component.HeaderHome
import com.tearas.expanemanagementjc.presentation.home.component.ListExpense
import com.tearas.expanemanagementjc.presentation.home.component.SearchBottomSheet
import com.tearas.expanemanagementjc.ui.theme.WhiteGray
import com.tearas.expanemanagementjc.ui.theme.isNightMode
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun HomeScreen(
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    viewModel: HomeViewModel,
    mapExpense: Map<ProfitLoss, List<ExpenseDto>> = emptyMap(),
    totalProfitLoss: ProfitLoss,
    onClickItem: (Int) -> Unit
) {
    val scope = rememberCoroutineScope()
    val state = rememberModalBottomSheetState()
    if (state.isVisible) {
        scope.launch { state.expand() }
        SearchBottomSheet(
            viewModel.state.searchExpenses,
            state,
            onDismiss = { scope.launch { state.hide() } },
            onClickItem = onClickItem,
            onSearch = { viewModel.onEvent(HomeEvent.SearchExpenses(it)) }
        )
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(WhiteGray),
        topBar = {
            HeaderHome(totalProfitLoss.spend, totalProfitLoss.collect, totalProfitLoss.surplus,
                onSearchClick = {
                    viewModel.onEvent(
                        HomeEvent.SearchExpenses(
                            System.currentTimeMillis().toString()
                        )
                    )
                    scope.launch { state.expand() }
                },
                onDateClick = { m, y ->
                    viewModel.onEvent(HomeEvent.SelectExpenses(m, y))
                })
        }
    ) { it ->
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(0.2.dp)
                    .background(if (isNightMode()) Color.White else Color.DarkGray)
            )
            if (mapExpense.isEmpty()) AnimNotFound()
            ListExpense(
                sharedTransitionScope,
                animatedContentScope,
                modifier = Modifier.padding(15.dp),
                mapExpense = mapExpense,
                onCLickItem = {
                    onClickItem(it)
                })
        }
    }
}



