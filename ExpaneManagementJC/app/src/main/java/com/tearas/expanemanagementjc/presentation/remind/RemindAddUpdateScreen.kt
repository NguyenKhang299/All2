package com.tearas.expanemanagementjc.presentation.remind

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tearas.expanemanagementjc.R
import com.tearas.expanemanagementjc.domain.model.RepeatType
import com.tearas.expanemanagementjc.presentation.remind.component.SettingItem
import com.tearas.expanemanagementjc.presentation.remind.component.TextFieldRemind
import com.tearas.expanemanagementjc.presentation.remind.component.TopBarRemind
import com.tearas.expanemanagementjc.presentation.remind.component.WheelTimePickerRemind
import com.tearas.expanemanagementjc.ui.theme.Blue
import com.tearas.expanemanagementjc.ui.theme.dynamicCardColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetOptionsModeRepeatTime(
    repeatSelected: RepeatType,
    onClick: (RepeatType, Int) -> Unit,
    onDismissRequest: () -> Unit
) {
    ModalBottomSheet(onDismissRequest = { onDismissRequest() }) {
        val repeatTypes = listOf(
            RepeatType.DAILY to R.string.repeat_daily,
            RepeatType.WEEKLY to R.string.repeat_weekly,
            RepeatType.ONCE to R.string.repeat_once
        )
        repeatTypes.forEach { (repeatType, stringResId) ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onClick(repeatType, stringResId)
                        onDismissRequest()
                    }
                    .background(if (repeatType == repeatSelected) Color.Blue.copy(alpha = 0.1f) else Color.Transparent)
                    .padding(15.dp)) {
                Text(
                    text = stringResource(id = stringResId),
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun RemindAddUpdateScreen(
    viewModel: RemindAddUpdateViewModel,
    id: Long? = null,
    onBack: () -> Unit
) {
    var idRes by remember {
        mutableIntStateOf(R.string.repeat_once)
    }
    LaunchedEffect(Unit) {
        if (id != null) viewModel.onEvent(RemindAddUpdateEvent.GetRemind(id))
    }

    LaunchedEffect(viewModel.state) {
        idRes = when (viewModel.state.remindModel.repeatType) {
            RepeatType.DAILY -> R.string.repeat_daily
            RepeatType.ONCE -> R.string.repeat_once
            RepeatType.WEEKLY -> R.string.repeat_weekly
        }
    }
    var showRepeatType by remember {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()
    if (showRepeatType) BottomSheetOptionsModeRepeatTime(
        repeatSelected = viewModel.state.remindModel.repeatType,
        onClick = { type, idRes ->
            viewModel.updateRepeatType(type)
        }) {
        showRepeatType = false
    }
    Scaffold(modifier = Modifier
        .imePadding()
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background), topBar = {
        TopBarRemind(
            onClickDone = {
                if (id != null) {
                    viewModel.onEvent(RemindAddUpdateEvent.UpdateRemind)
                } else {
                    viewModel.onEvent(RemindAddUpdateEvent.AddRemind)
                }
                scope.launch {
                    delay(500)
                    onBack()
                }
            },
            onClickBack = { onBack() })
    }) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()

        ) {
            WheelTimePickerRemind(id != null, viewModel.state.remindModel.time) {
                viewModel.updateTime(it)
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 5.dp),
                horizontalAlignment = Alignment.Start
            ) {
                SettingItem(
                    title = stringResource(id = R.string.repeat_mode),
                    onClick = {
                        showRepeatType = true
                    },
                    content = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                modifier = Modifier.padding(15.dp),
                                text = stringResource(id = idRes),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = Color.LightGray,
                                    fontStyle = FontStyle.Normal,
                                    fontWeight = FontWeight.Normal
                                )
                            )
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                contentDescription = null,
                                tint = Color.LightGray
                            )
                        }
                    }
                )
                SettingItem(
                    title = stringResource(id = R.string.delete_after_reminder),
                    content = {
                        Switch(
                            checked = viewModel.isDelete, colors = SwitchDefaults.colors(
                                uncheckedBorderColor = Color.Transparent,
                                checkedBorderColor = Color.Transparent,
                                checkedTrackColor = Color.LightGray,
                                checkedThumbColor = Blue,
                                uncheckedThumbColor = Blue,
                                uncheckedTrackColor = Color.LightGray,
                            ),
                            onCheckedChange = { viewModel.updateDeleteBeforeStart(it) })
                    }
                )
                TextFieldRemind(
                    titleId = R.string.title,
                    placeholderTextId = R.string.reminder_item_name,
                    value = viewModel.title
                ) {
                    viewModel.updateTitle(it)
                }
                TextFieldRemind(
                    modifier = Modifier.padding(top = 15.dp),
                    titleId = R.string.note,
                    placeholderTextId = R.string.note,
                    value = viewModel.note
                ) {
                    viewModel.updateNote(it)
                }
            }
        }
    }
}


@Preview
@Composable
private fun RemindScreenPreview() {

}