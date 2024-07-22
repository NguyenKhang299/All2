package com.tearas.expanemanagementjc.presentation.report

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tearas.expanemanagementjc.presentation.common.AnimNotFound
import com.tearas.expanemanagementjc.presentation.home.component.CardItem
import com.tearas.expanemanagementjc.presentation.report.componet.BottomBarReport
import com.tearas.expanemanagementjc.presentation.report.componet.ListReport
import com.tearas.expanemanagementjc.presentation.report.componet.TopBarReport
import com.tearas.expanemanagementjc.service.ExcelService
import com.tearas.expanemanagementjc.ui.theme.ExpaneManagementJCTheme
import kotlinx.coroutines.launch
import org.apache.poi.ss.util.CellRangeAddress
import org.apache.poi.xssf.usermodel.XSSFWorkbook

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ReportScreen(viewModel: ReportViewModel) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        viewModel.onEvent(ReportEvent.StatisticalReport)
    }

    Scaffold(topBar = {
        TopBarReport { }
    }, bottomBar = {
        BottomBarReport(viewModel.state.totalProfitLoss)
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(top = 15.dp)
        ) {
            if (viewModel.state.statisticalReport.isEmpty()) {
                AnimNotFound()
            } else {
                Card {
                    ListReport(viewModel.state.statisticalReport)
                }
            }
        }
    }
}

@Preview
@Composable
private fun ReportScreenPreview() {
    ExpaneManagementJCTheme {
        val viewModel: ReportViewModel = hiltViewModel()
        ReportScreen(viewModel)
    }
}