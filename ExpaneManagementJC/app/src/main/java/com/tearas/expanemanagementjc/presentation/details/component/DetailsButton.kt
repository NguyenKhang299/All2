package com.tearas.expanemanagementjc.presentation.details.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tearas.expanemanagementjc.ui.theme.ExpaneManagementJCTheme
import com.tearas.expanemanagementjc.ui.theme.dynamicBodyColor

@Composable
fun DetailsButton(modifier: Modifier, text: String, onClick: () -> Unit) {
    Button(
        modifier = modifier.fillMaxWidth(),
        onClick = { onClick() }, shape = RoundedCornerShape(0.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = dynamicBodyColor()
        )
    ) {
        Text(text = text, color = Color.White)
    }
}

@Preview
@Composable
private fun DetailsButtonPreview() {
    ExpaneManagementJCTheme {
        DetailsButton(modifier = Modifier, "Edit") {}
    }
}