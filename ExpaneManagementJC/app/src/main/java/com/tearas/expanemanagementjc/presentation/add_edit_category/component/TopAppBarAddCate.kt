package com.tearas.expanemanagementjc.presentation.add_edit_category.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.tearas.expanemanagementjc.R
import com.tearas.expanemanagementjc.ui.theme.dynamicTopAppBarColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarAddCate(
    title: String,
    enableDone: Boolean = false,
    onClickBack: () -> Unit,
    onClickDone: () -> Unit
) {
    val context = LocalContext.current
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = { onClickBack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = dynamicTopAppBarColor(), navigationIconContentColor = Color.White
        ),
        title = {
            Text(
                style = MaterialTheme.typography.titleLarge,
                text = title,
                color = Color.White
            )
        },
        actions = {
            IconButton(onClick = { onClickDone() }, enabled = enableDone) {
                Icon(
                    imageVector = Icons.Filled.Done, contentDescription = null,
                    tint = if (enableDone) Color.White else Color.DarkGray
                )
            }
        }
    )
}