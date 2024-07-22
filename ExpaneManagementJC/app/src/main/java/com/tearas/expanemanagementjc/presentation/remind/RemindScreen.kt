package com.tearas.expanemanagementjc.presentation.remind

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tearas.expanemanagementjc.R
import com.tearas.expanemanagementjc.graph.AddGraph
import com.tearas.expanemanagementjc.presentation.common.AnimNotFound
import com.tearas.expanemanagementjc.presentation.remind.component.ListRemind
import com.tearas.expanemanagementjc.ui.theme.Blue
import com.tearas.expanemanagementjc.ui.theme.dynamicAppBar
import com.tearas.expanemanagementjc.ui.theme.dynamicTextColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemindScreen(
    viewModel: RemindViewModel,
    onDestination: (String) -> Unit,
    onBack: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.onEvent(RemindEvent.GetReminds)
    }
    Scaffold(bottomBar = {
        Column {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(0.2.dp)
                    .background(dynamicAppBar())
            )
            Button(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .padding(5.dp),
                onClick = { onDestination(AddGraph.AddRemindScreen.route) },
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Blue)
            ) {
                Text(text = stringResource(id = R.string.add_reminders), color = Color.White)
            }
        }
    }, topBar = {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.reminder_list),
                    color = Color.White
                )
            },
            navigationIcon = {
                IconButton(onClick = { onBack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }, colors = TopAppBarDefaults.topAppBarColors(containerColor = Blue)
        )
    }) { it ->
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            if (viewModel.sate.reminds.isEmpty()) {
                AnimNotFound()
            } else {
                ListRemind(listRemind = viewModel.sate.reminds, onItemClick = {
                    onDestination(AddGraph.EditRemindScreen.route + "/${it.id}")
                }, onItemDismiss = { viewModel.onEvent(RemindEvent.DeleteRemind(it)) })
            }
        }
    }
}

@Preview
@Composable
private fun RemindScreenPre() {

}