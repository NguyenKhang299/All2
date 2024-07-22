package com.tearas.expanemanagementjc.presentation.setting.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tearas.expanemanagementjc.ui.theme.WhiteGray
import com.tearas.expanemanagementjc.ui.theme.dynamicCardColor
import com.tearas.expanemanagementjc.ui.theme.isNightMode
import com.tearas.expanemanagementjc.ui.theme.md_theme_dark_background_Card

@Composable
fun SettingCard(content: @Composable () -> Unit) {
    Card(
        modifier = Modifier
            .padding(top = 15.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = dynamicCardColor())
    ) {
        Column(Modifier.padding(15.dp)) {
            content()
        }
    }
}