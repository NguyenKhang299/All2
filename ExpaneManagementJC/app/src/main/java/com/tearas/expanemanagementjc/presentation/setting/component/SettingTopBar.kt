package com.tearas.expanemanagementjc.presentation.setting.component

import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.tearas.expanemanagementjc.R
import com.tearas.expanemanagementjc.ui.theme.Blue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingTopBar(title: String, onClickNav: () -> Unit) {
    TopAppBar(
        title = { Text(text = title, color = Color.White) },
        colors = TopAppBarDefaults.topAppBarColors(Blue),
        navigationIcon = {
            IconButton(onClick = { onClickNav() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    tint = Color.White,
                    contentDescription = null
                )
            }
        }
    )
}

