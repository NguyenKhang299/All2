package com.tearas.expanemanagementjc.presentation.common


import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.tearas.expanemanagementjc.ui.theme.Blue
import com.tearas.expanemanagementjc.ui.theme.ExpaneManagementJCTheme

@Preview(showBackground = true)
@Composable
private fun TabIndicatorPreView() {
    ExpaneManagementJCTheme {
        var tabSelected by remember {
            mutableIntStateOf(0)
        }
        TabIndicator(
            modifier = Modifier,
            listOf(
                com.tearas.expanemanagementjc.presentation.add_edit_expense.component.TabItem("Tab 1", 0), com.tearas.expanemanagementjc.presentation.add_edit_expense.component.TabItem("Tab 2", 1), com.tearas.expanemanagementjc.presentation.add_edit_expense.component.TabItem("Tab 3", 2)
            ), tabSelected = tabSelected
        ) {
            tabSelected = it
        }
    }
}

@Composable
fun TabItem(text: String, selected: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(contentAlignment = Alignment.Center,
        modifier = modifier.clickable(indication = rememberRipple(color = Blue),
            interactionSource = remember { MutableInteractionSource() }) {
            onClick()
        }) {
        Text(
            text = text, fontSize = 16.sp, color = if (selected) Color.White else Color.Black,
            style = MaterialTheme.typography.labelLarge
        )
    }
}
