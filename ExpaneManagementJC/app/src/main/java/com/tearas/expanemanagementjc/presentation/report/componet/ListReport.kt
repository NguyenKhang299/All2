package com.tearas.expanemanagementjc.presentation.report.componet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.tearas.expanemanagementjc.domain.model.ProfitLoss
import com.tearas.expanemanagementjc.ui.theme.ExpaneManagementJCTheme
import com.tearas.expanemanagementjc.ui.theme.dynamicCardColor
import com.tearas.expanemanagementjc.ui.theme.isNightMode


@Composable
fun ListReport(profitLoss: Map<Int, List<ProfitLoss>> = emptyMap()) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        ColumnTable()
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(if (isNightMode()) MaterialTheme.colorScheme.background else Color.White)
        ) {
            profitLoss.entries.forEachIndexed { index, entry ->
                item {
                    ReportRow(
                        entry.key,
                        profitLoss = entry.value
                    )
                }
            }
        }
    }
}


@Preview
@Composable
private fun ListReportPre() {
    ExpaneManagementJCTheme {
        ListReport()
    }
}