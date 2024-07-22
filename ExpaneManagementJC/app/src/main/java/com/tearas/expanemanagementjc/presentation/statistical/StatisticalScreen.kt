package com.tearas.expanemanagementjc.presentation.statistical

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleResumeEffect
import com.tearas.expanemanagementjc.R
import com.tearas.expanemanagementjc.domain.model.TransactionType
import com.tearas.expanemanagementjc.presentation.add_edit_expense.component.TabItem
import com.tearas.expanemanagementjc.presentation.common.AnimNotFound
import com.tearas.expanemanagementjc.presentation.common.AutoResizedText
import com.tearas.expanemanagementjc.presentation.common.DatePickerDialogCustom
import com.tearas.expanemanagementjc.presentation.piechart.DetailsPieChart
import com.tearas.expanemanagementjc.presentation.piechart.PieChart
import com.tearas.expanemanagementjc.presentation.piechart.colorsChart
import com.tearas.expanemanagementjc.presentation.common.TabIndicator
import com.tearas.expanemanagementjc.presentation.common.YearPickerDialogCustom
import com.tearas.expanemanagementjc.presentation.statistical.componet.Item
import com.tearas.expanemanagementjc.presentation.piechart.topFourAndOthers
import com.tearas.expanemanagementjc.presentation.statistical.componet.DetailsCategoryBottomSheet
import com.tearas.expanemanagementjc.presentation.statistical.componet.ListItemStatistical
import com.tearas.expanemanagementjc.presentation.statistical.componet.TopAppBarStatistical
import com.tearas.expanemanagementjc.ui.theme.Blue
import com.tearas.expanemanagementjc.ui.theme.ExpaneManagementJCTheme
import com.tearas.expanemanagementjc.utils.format
import java.util.Calendar

@Composable
fun ShowDatePickerDialog(
    showDialog: Boolean,
    tabSelected: Int,
    month: Int,
    year: Int,
    onDismissRequest: () -> Unit,
    onConfirmed: (Int, Int) -> Unit
) {
    if (showDialog) {
        if (tabSelected == 0) {
            DatePickerDialogCustom(
                month = month,
                year = year,
                onDismissRequest = onDismissRequest,
                onConfirmed = onConfirmed
            )
        } else {
            YearPickerDialogCustom(year = year,
                onDismissRequest = onDismissRequest,
                onConfirmed = { y -> onConfirmed(month, y) })
        }
    }
}


@Composable
fun StatisticalScreen(viewModel: StatisticalViewModel) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance().apply { timeInMillis = System.currentTimeMillis() }
    var month by remember { mutableIntStateOf(calendar[Calendar.MONTH] + 1) }
    var year by remember { mutableIntStateOf(calendar[Calendar.YEAR]) }
    var tabSelected by remember { mutableIntStateOf(0) }
    var transactionType by remember { mutableStateOf(TransactionType.SPEND) }
    var idCate by remember { mutableLongStateOf(0) }
    var showDialog by remember { mutableStateOf(false) }
    var showBottomSheetCate by remember { mutableStateOf(false) }


    LaunchedEffect(tabSelected, transactionType, month, year, idCate) {
        if (showBottomSheetCate) {
            if (tabSelected == 0) {
                viewModel.onEvent(StatisticalEvent.StatisticalDetailsCategoryByMonth(idCate, transactionType, month, year))
            } else {
                viewModel.onEvent(StatisticalEvent.StatisticalDetailsCategoryByYear(idCate, transactionType, year))
            }
        } else {
            if (tabSelected == 0) {
                viewModel.onEvent(StatisticalEvent.StatisticalMonth(transactionType, month, year))
            } else {
                viewModel.onEvent(StatisticalEvent.StatisticalYear(transactionType, year))
            }
        }
    }

    val widthPieChart = LocalConfiguration.current.screenWidthDp.dp * 4 / 10

    if (showBottomSheetCate) DetailsCategoryBottomSheet(viewModel) {
        showBottomSheetCate = false
    }
    ShowDatePickerDialog(showDialog = showDialog,
        tabSelected = tabSelected,
        month = month,
        year = year,
        onDismissRequest = { showDialog = false },
        onConfirmed = { m, y ->
            month = m
            year = y
            showDialog = false
        })

    Scaffold(topBar = {
        TopAppBarStatistical(onClickChangeType = {
            transactionType = it
        }, onClickDateDialog = {
            showDialog = true
        })
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(15.dp),
            verticalArrangement = Arrangement.Top
        ) {
            TabIndicator(
                listTabs = listOf(
                    TabItem(stringResource(R.string.month), 0),
                    TabItem(stringResource(R.string.year), 1)
                ), tabSelected = tabSelected
            ) {
                tabSelected = it
            }
            if (viewModel.state.statisticalDto.isEmpty()) AnimNotFound() else {
                Row(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .height(widthPieChart)
                ) {
                    Box(
                        modifier = Modifier
                            .padding(10.dp)
                            .weight(4f)
                            .aspectRatio(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        PieChart(
                            radiusOuter = widthPieChart / 2,
                            chartBarWidth = 25.dp,
                            data = viewModel.state.detailsPieChart
                        )
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 25.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            AnimatedVisibility(
                                visible = viewModel.maxValue != 0.0
                            ) {
                                Text(
                                    text = viewModel.maxValue.format(),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                )
                            }
                        }
                    }

                    DetailsPieChart(
                        modifier = Modifier
                            .weight(6f)
                            .padding(start = 20.dp),
                        data = topFourAndOthers(
                            context, viewModel.state.detailsPieChart
                        ),
                        colors = colorsChart
                    )
                }

                if (viewModel.state.statisticalDto.isNotEmpty()) ListItemStatistical(
                    modifier = Modifier.padding(top = 20.dp),
                    maxValue = viewModel.state.statisticalDto[0].totalExpenses,
                    data = viewModel.state.statisticalDto
                ) {
                    idCate = it.category_id
                    showBottomSheetCate = true
                }
            }
        }
    }
}

@Preview
@Composable
private fun StatisticalScreenPreview() {
    ExpaneManagementJCTheme {
        val viewModel: StatisticalViewModel = hiltViewModel()
        StatisticalScreen(viewModel)
    }
}