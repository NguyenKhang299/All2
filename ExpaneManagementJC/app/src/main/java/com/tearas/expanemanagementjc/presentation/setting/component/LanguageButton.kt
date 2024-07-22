package com.tearas.expanemanagementjc.presentation.setting.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tearas.expanemanagementjc.ui.theme.Blue

@Composable
fun LanguageButton(selected: Boolean, text: String, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        modifier = Modifier,
        border = BorderStroke(1.dp, Blue),
        colors = ButtonDefaults.buttonColors(containerColor = if (!selected) Color.White else Blue)
    ) {
        Text(text = text, color = if (selected) Color.White else Blue)
    }
}