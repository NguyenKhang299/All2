package com.tearas.expanemanagementjc.presentation.report.componet

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.tearas.expanemanagementjc.R
import com.tearas.expanemanagementjc.ui.theme.Blue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarReport(showBadge: Boolean = false, onClickDate: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.report),
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }, actions = {

        }, colors = TopAppBarDefaults.topAppBarColors(containerColor = Blue)
    )
}