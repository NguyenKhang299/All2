package com.tearas.expanemanagementjc.presentation.report.componet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tearas.expanemanagementjc.R
import com.tearas.expanemanagementjc.domain.model.ProfitLoss
import com.tearas.expanemanagementjc.presentation.home.component.CardItem
import com.tearas.expanemanagementjc.ui.theme.WhiteGray
import com.tearas.expanemanagementjc.ui.theme.dynamicTextColor
import com.tearas.expanemanagementjc.utils.format

@Composable
fun BottomBarReport(
    totalProfitLoss: ProfitLoss
) {
   CardItem {
       Column(
           modifier = Modifier
               .fillMaxWidth()
               .padding(10.dp),
       ) {
           Text(
               text = stringResource(id = R.string.expense) + ": ${totalProfitLoss.spend.format()}",
               color = dynamicTextColor()
           )
           Text(
               text = stringResource(id = R.string.income) + ": ${totalProfitLoss.collect.format()}",
               color =dynamicTextColor()
           )

           Row(
               modifier = Modifier.fillMaxWidth(),
               horizontalArrangement = Arrangement.SpaceBetween
           ) {
               Text(
                   text = stringResource(id = R.string.total_balance) + ": ",
                   color = dynamicTextColor(),
                   fontSize = 16.sp
               )
               Text(
                   text = totalProfitLoss.surplus.format(),
                   color = dynamicTextColor(),
                   maxLines = 2,
                   overflow = TextOverflow.Ellipsis,
                   fontSize = 18.sp
               )
           }
       }
   }
}