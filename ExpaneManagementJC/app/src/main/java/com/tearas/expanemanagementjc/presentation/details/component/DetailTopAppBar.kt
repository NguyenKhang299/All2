package com.tearas.expanemanagementjc.presentation.details.component

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.tearas.expanemanagementjc.ui.theme.Blue
import com.tearas.expanemanagementjc.ui.theme.dynamicTopAppBarColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTopAppBar(onClickNav: () -> Unit) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = dynamicTopAppBarColor(),
            navigationIconContentColor = Color.White
        ),
        title = {
            Text(
                style = MaterialTheme.typography.titleLarge,
                text = "Details",
                color = Color.White
            )
        }, navigationIcon = {
            IconButton(onClick = { onClickNav() }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
            }
        })
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DetailTopAppBarPreview() {
    DetailTopAppBar(){}
}