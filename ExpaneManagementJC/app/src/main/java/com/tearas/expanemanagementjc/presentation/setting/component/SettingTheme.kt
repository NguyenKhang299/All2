package com.tearas.expanemanagementjc.presentation.setting.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tearas.expanemanagementjc.R
import com.tearas.expanemanagementjc.domain.model.ThemeMode
import com.tearas.expanemanagementjc.ui.theme.Blue
import com.tearas.expanemanagementjc.ui.theme.HeaderColor
import com.tearas.expanemanagementjc.ui.theme.WhiteGray2
import com.tearas.expanemanagementjc.ui.theme.isNightMode

@Composable
fun SettingTheme(themeSelected: ThemeMode, onThemeSelected: (ThemeMode) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(id = R.string.theme), modifier = Modifier.padding(5.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = stringResource(id = R.string.dark_mode), modifier = Modifier.padding(5.dp))
            Switch(
                checked = themeSelected == ThemeMode.DARK_MODE, onCheckedChange = {
                    onThemeSelected(if (!it) ThemeMode.LIGHT_MODE else ThemeMode.DARK_MODE)
                }, colors = SwitchDefaults.colors(
                    uncheckedBorderColor = Color.Transparent,
                    checkedBorderColor = Color.Transparent,
                    checkedTrackColor = Color.LightGray,
                    checkedThumbColor = Blue,
                    uncheckedThumbColor = Blue,
                    uncheckedTrackColor = Color.LightGray,
                 )
            )
        }
    }
}