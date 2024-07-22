package com.tearas.expanemanagementjc.presentation.remind.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.tearas.expanemanagementjc.R
import com.tearas.expanemanagementjc.ui.theme.dynamicAppBar
import com.tearas.expanemanagementjc.ui.theme.dynamicTextColor
import com.tearas.expanemanagementjc.ui.theme.dynamicTopAppBarColor
import com.tearas.expanemanagementjc.ui.theme.isNightMode
import com.tearas.expanemanagementjc.ui.theme.md_theme_dark_background_appbar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarRemind(
    onClickBack: () -> Unit,
    onClickDone: () -> Unit
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = if (isNightMode()) md_theme_dark_background_appbar else Color.White
        ),
        title = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = stringResource(id = R.string.add_reminders),
                    style = MaterialTheme.typography.titleLarge,
                    color = dynamicTextColor()
                )
            }
        },
        actions = {
            IconButton(onClick = { onClickDone() }) {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = null,
                    tint = if (isNightMode()) Color.White else Color.Black
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = { onClickBack() }) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = null,
                    tint = if (isNightMode()) Color.White else Color.Black
                )
            }
        }
    )
}