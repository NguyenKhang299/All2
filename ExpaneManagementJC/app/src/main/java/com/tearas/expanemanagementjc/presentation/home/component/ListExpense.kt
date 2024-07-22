package com.tearas.expanemanagementjc.presentation.home.component

import android.util.Log
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tearas.expanemanagementjc.data.dto.ExpenseDto
import com.tearas.expanemanagementjc.domain.model.ProfitLoss
import com.tearas.expanemanagementjc.ui.theme.isNightMode
import com.tearas.expanemanagementjc.ui.theme.md_theme_dark_background
import com.tearas.expanemanagementjc.ui.theme.md_theme_dark_inverseOnSurface

@OptIn(ExperimentalFoundationApi::class, ExperimentalSharedTransitionApi::class)
@Composable
fun ListExpense(
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedContentScope: AnimatedContentScope? = null,
    modifier: Modifier,
    mapExpense: Map<ProfitLoss, List<ExpenseDto>>,
    onCLickItem: (Int) -> Unit
) {
    val expandedState = remember {
        mutableStateListOf<Boolean>()
    }
    LaunchedEffect(mapExpense) {
        expandedState.clear()
        repeat(mapExpense.size) {
            expandedState.add(true)
        }
    }
    if (expandedState.size > 0) {
        LazyColumn(modifier = modifier) {
            mapExpense.forEach { (profitLoss, expenses) ->

                item {
                    Spacer(modifier = Modifier.height(15.dp))
                }

                stickyHeader {
                    val index = mapExpense.indexHeader(profitLoss = profitLoss)
                    ItemExpenseHeader(profitLoss = profitLoss) {
                        expandedState[index] = !expandedState[index]
                    }
                }

                item {
                    CardItem {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .animateContentSize()
                        ) {
                            val index = mapExpense.indexHeader(profitLoss = profitLoss)
                            AnimatedVisibility(visible = expandedState[index]) {
                                Column(Modifier.background(if (isNightMode()) md_theme_dark_background else Color.White)) {
                                    expenses.forEach { expense ->
                                        ItemExpense(
                                            sharedTransitionScope,
                                            animatedContentScope,
                                            expense,
                                            onClickItem = {
                                                onCLickItem(expense.expenseId.toInt())
                                            })
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun Map<ProfitLoss, List<ExpenseDto>>.indexHeader(profitLoss: ProfitLoss) =
    entries.indexOfFirst { it.key == profitLoss }