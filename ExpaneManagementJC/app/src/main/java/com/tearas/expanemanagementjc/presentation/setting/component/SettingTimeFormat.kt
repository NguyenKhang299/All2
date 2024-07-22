package com.tearas.expanemanagementjc.presentation.setting.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tearas.expanemanagementjc.R
import com.tearas.expanemanagementjc.domain.model.TimeFormat


@Composable
fun SettingTimeFormat(formatSelected: TimeFormat, onSelected: (TimeFormat) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(id = R.string.time_format), modifier = Modifier.padding(5.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = formatSelected == TimeFormat.H12, onClick = {
                onSelected(TimeFormat.H12)
            })
            Text(
                text = "12h",
                modifier = Modifier.padding(5.dp),
                style = MaterialTheme.typography.titleMedium
            )
            RadioButton(selected = formatSelected == TimeFormat.H24, onClick = {
                onSelected(TimeFormat.H24)
            })
            Text(
                text = "24h",
                modifier = Modifier.padding(5.dp),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}