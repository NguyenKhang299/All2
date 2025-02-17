package com.tearas.expanemanagementjc.presentation.report.componet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tearas.expanemanagementjc.domain.model.ProfitLoss
import com.tearas.expanemanagementjc.ui.theme.dynamicCardColor
import com.tearas.expanemanagementjc.utils.getMonthDate

@Composable
fun ReportRow(year: Int, profitLoss: List<ProfitLoss>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            ,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = year.toString(),
            modifier = Modifier.weight(1.5f),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )


        Column(modifier = Modifier.weight(8.5f)) {
            profitLoss.forEachIndexed { index, item ->
                ReportDetailRow(month = item.date.getMonthDate(), item)
            }
        }
    }
}