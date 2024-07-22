package com.tearas.expanemanagementjc.presentation.report.componet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tearas.expanemanagementjc.domain.model.ProfitLoss
import com.tearas.expanemanagementjc.ui.theme.WhiteGray
import com.tearas.expanemanagementjc.ui.theme.dynamicCardColor
import com.tearas.expanemanagementjc.utils.format

@Composable
fun ReportDetailRow(month: String, profitLoss: ProfitLoss) {

    Column {
        Row(
            modifier = Modifier
                .padding(vertical = 10.dp)
                .fillMaxWidth()
                ,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = month, modifier = Modifier.weight(1.5f), textAlign = TextAlign.Center)
            Text(
                text = profitLoss.spend.format(),
                modifier = Modifier.weight(7.5f / 3f),
                textAlign = TextAlign.Center
            )
            Text(
                text = profitLoss.collect.format(),
                modifier = Modifier.weight(7.5f / 3f),
                textAlign = TextAlign.Center
            )
            Text(
                text = profitLoss.surplus.format(),
                modifier = Modifier.weight(7.5f / 3f),
                textAlign = TextAlign.Center
            )
        }
    }
}