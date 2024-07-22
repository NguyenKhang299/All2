package com.tearas.expanemanagementjc.presentation.report.componet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.tearas.expanemanagementjc.R
import com.tearas.expanemanagementjc.presentation.home.component.CardItem
import com.tearas.expanemanagementjc.ui.theme.HeaderColor
import com.tearas.expanemanagementjc.ui.theme.isNightMode
import com.tearas.expanemanagementjc.ui.theme.md_theme_dark_background_Card
import com.tearas.expanemanagementjc.ui.theme.md_theme_dark_background_appbar

@Composable
fun ColumnTable() {
    CardItem {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(if (!isNightMode()) Color.White else md_theme_dark_background_appbar),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            HeaderText(text = stringResource(id = R.string.year), weight = 1.5f)
            HeaderText(text = stringResource(id = R.string.month), weight = 1.5f)
            HeaderText(text = stringResource(id = R.string.expense), weight = 7 / 3f)
            HeaderText(text = stringResource(id = R.string.income), weight = 7 / 3f)
            HeaderText(text = stringResource(id = R.string.surplus), weight = 7 / 3f)
        }
    }
}