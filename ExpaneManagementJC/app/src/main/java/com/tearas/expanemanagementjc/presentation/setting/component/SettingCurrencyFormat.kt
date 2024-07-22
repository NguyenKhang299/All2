package com.tearas.expanemanagementjc.presentation.setting.component

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.tearas.expanemanagementjc.R
import com.tearas.expanemanagementjc.domain.model.CurrencyFormat
import com.tearas.expanemanagementjc.ui.theme.dynamicTextColor

@Composable
fun SettingCurrencyFormat(currencyFormat: CurrencyFormat, onSelected: (CurrencyFormat) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    SettingDropdown(
        label = stringResource(id = R.string.currency_format),
        selectedText = currencyFormat.format,
        expanded = expanded,
        onExpandChange = { expanded = it }
    ) {
        CurrencyFormat.entries.forEach {
            DropdownMenuItem(
                onClick = {
                    expanded = false
                    onSelected(it)
                },
                text = { Text(text = it.format, color = dynamicTextColor()) })
        }
    }
}