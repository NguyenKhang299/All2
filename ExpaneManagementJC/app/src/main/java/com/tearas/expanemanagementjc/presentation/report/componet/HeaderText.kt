package com.tearas.expanemanagementjc.presentation.report.componet

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@Composable
fun RowScope.HeaderText(text: String, weight: Float) {
    Text(
        text = text,
        modifier = Modifier.weight(weight),
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,

    )
}