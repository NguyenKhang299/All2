package com.tearas.expanemanagementjc.presentation.setting

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.ActivityManager
import android.content.Context.ACTIVITY_SERVICE
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import com.tearas.expanemanagementjc.ExpenseApplication
import com.tearas.expanemanagementjc.R
import com.tearas.expanemanagementjc.domain.model.Language
import com.tearas.expanemanagementjc.graph.AddGraph
import com.tearas.expanemanagementjc.graph.MainGraph
import com.tearas.expanemanagementjc.presentation.common.DialogWarning
import com.tearas.expanemanagementjc.presentation.password.component.PasscodeViewType
import com.tearas.expanemanagementjc.presentation.setting.component.BottomSheetOptionsPass
import com.tearas.expanemanagementjc.presentation.setting.component.DeleteAllButton
import com.tearas.expanemanagementjc.presentation.setting.component.SettingCard
import com.tearas.expanemanagementjc.presentation.setting.component.SettingCurrencyFormat
import com.tearas.expanemanagementjc.presentation.setting.component.SettingItem
import com.tearas.expanemanagementjc.presentation.setting.component.SettingLanguage
import com.tearas.expanemanagementjc.presentation.setting.component.SettingTheme
import com.tearas.expanemanagementjc.presentation.setting.component.SettingTimeFormat
import com.tearas.expanemanagementjc.presentation.setting.component.SettingTopBar
import com.tearas.expanemanagementjc.utils.FileHelper.openFile
import kotlinx.coroutines.delay


@Composable
fun SettingScreen(
    viewModel: SettingViewModel,
    onDestination: (String) -> Unit,
    onBack: () -> Unit
) {
    val settingOptions = viewModel.settingOptions
    val context = LocalContext.current
    var showLoading by remember {
        mutableStateOf(false)
    }
    var onShowRemove by remember {
        mutableStateOf(false)
    }
    var showDialogDeleteAll by remember {
        mutableStateOf(false)
    }
    if (showDialogDeleteAll) {
        DialogWarning(
            onDismissRequest = { showDialogDeleteAll = false },
            onConfirmation = {
                Runtime.getRuntime().exec("pm clear " + context.packageName)
            },
            dialogTitle = stringResource(id = R.string.warning),
            dialogText = stringResource(id = R.string.mess_kill_app),
            colorConfirm = Color.Red,
        )
    }
    if (onShowRemove) BottomSheetOptionsPass(onRemove = {
        viewModel.onEvent(SettingEvent.DeletePass)
        onShowRemove = false
    }) {
        onShowRemove = false
    }
    val rq =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) {
            if (it) {
                showLoading = true
                viewModel.onEvent(SettingEvent.ExportExcel)
            } else {
                Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }

    Scaffold(
        topBar = {
            SettingTopBar(stringResource(id = R.string.setting)) { onBack() }
        },
        bottomBar = {
            DeleteAllButton(stringResource(id = R.string.delete_all_data)) {
                showDialogDeleteAll = true
            }
        }
    ) {
        LaunchedEffect(key1 = viewModel.path, Unit) {
            delay(500)
            if (showLoading) {
                if (viewModel.path != null) {
                    try {
                        openFile(context, viewModel.path!!)
                    } catch (e: Exception) {
                    }
                }
                showLoading = false
                viewModel.path = null
            }
        }

        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(15.dp)
        ) {
            SettingCard {
                SettingItem(
                    R.drawable.categories,
                    stringResource(id = R.string.setting_category)
                ) {
                    onDestination(AddGraph.SettingCategoryScreen.route)
                }
                SettingItem(R.drawable.padlock, stringResource(id = R.string.password)) {
                    if (ExpenseApplication.isPasscode) onShowRemove = true else onDestination(
                        MainGraph.PasswordScreen.route + "/${PasscodeViewType.TYPE_SET_PASSCODE}"
                    )
                }
                SettingItem(R.drawable.notification, stringResource(id = R.string.reminder)) {
                    onDestination(AddGraph.RemindScreen.route)
                }
                SettingItem(R.drawable.export, stringResource(id = R.string.export_excel)) {
                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q &&
                        ContextCompat.checkSelfPermission(
                            context,
                            WRITE_EXTERNAL_STORAGE
                        ) == PackageManager.PERMISSION_DENIED
                    ) {
                        rq.launch(WRITE_EXTERNAL_STORAGE)
                        return@SettingItem
                    }

                    showLoading = true
                    viewModel.onEvent(SettingEvent.ExportExcel)
                }
                SettingCurrencyFormat(settingOptions.currencyFormat) {
                    viewModel.updateCurrencyFormat(it)
                }
                SettingTimeFormat(settingOptions.timeFormat) {
                    viewModel.updateTimeFormat(it)
                }
            }
            SettingCard {
                SettingTheme(settingOptions.themeMode) {
                    viewModel.updateThemeMode(it)
                }
                SettingLanguage(settingOptions.language) {
                    viewModel.updateLang(it)
                    Language.setLocale(it, context)
                }
            }
        }
        if (showLoading) Dialog(
            onDismissRequest = { showLoading = false },
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false,
                usePlatformDefaultWidth = false
            )
        ) {
            CircularProgressIndicator()
        }
    }
}


