package com.tearas.expanemanagementjc.presentation.add_edit_expense

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.tearas.expanemanagementjc.data.dto.CategoryDto
import com.tearas.expanemanagementjc.data.mapper.mapToCategoryDto
import com.tearas.expanemanagementjc.presentation.add_edit_expense.component.BottomBarAddExpense
import com.tearas.expanemanagementjc.presentation.add_edit_expense.component.ListCategories
import com.tearas.expanemanagementjc.presentation.add_edit_expense.component.TopAddExpense

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AddExpenseScreen(
    viewModel: AddExpenseViewModel,
    isUpdate: Boolean = false,
    onNavClick: () -> Unit,
    onSettingClick: () -> Unit,
) {
    val pagerState = rememberPagerState(initialPage = 0)
    val context = LocalContext.current
    var itemCategorySelected by remember {
        mutableStateOf<CategoryDto?>(null)
    }

    LaunchedEffect(key1 = viewModel.expenseState.isSuccess, Unit) {
        if (viewModel.expenseState.isSuccess == true) {
            onNavClick()
        }
    }
    if (isUpdate && itemCategorySelected == null) {
        itemCategorySelected = viewModel.expenseState.expenseDto?.mapToCategoryDto()
    }
    Scaffold(
        topBar = {
            TopAddExpense(
                pagerState,
                viewModel.expense.isValidExpense(),
                onNavClick = { onNavClick() },
                onSettingClick = { onSettingClick() },
                onDoneClick = {
                    if (isUpdate)
                        viewModel.onEvent(EventAddExpense.UpdateExpense)
                    else
                        viewModel.onEvent(EventAddExpense.AddExpense)
                })
        },
        bottomBar = {
            BottomBarAddExpense(
                viewModel,
                itemCategorySelected,
                isUpdate
            )
        }) { paddingValues ->
        Column(modifier = Modifier) {
            HorizontalPager(
                state = pagerState,
                verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxHeight(),
                count = 2
            ) { it ->

                val categories = if (it == 0) {
                    viewModel.onEvent(EventAddExpense.SelectCategoriesDtoSpend)
                    viewModel.selectCategoriesSpend
                } else {
                    viewModel.onEvent(EventAddExpense.SelectCategoriesDtoCollect)
                    viewModel.selectCategoriesCollect
                }

                ListCategories(
                    listCategory = categories, itemCategorySelected
                ) {
                    viewModel.onCategoryChanged(it)
                    itemCategorySelected = it
                }
            }
        }
    }
}


