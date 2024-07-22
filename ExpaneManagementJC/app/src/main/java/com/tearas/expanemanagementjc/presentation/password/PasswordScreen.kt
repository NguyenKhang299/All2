package com.tearas.expanemanagementjc.presentation.password

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.tearas.expanemanagementjc.R
import com.tearas.expanemanagementjc.graph.AppRoute
import com.tearas.expanemanagementjc.presentation.password.component.PasscodeViewType
import com.tearas.expanemanagementjc.presentation.password.component.ViewPhonePassword

@Composable
fun PasswordScreen(
    viewModel: PasswordViewModel,
    onNavigate: (String) -> Unit,
    onBack: () -> Unit,
) {
    val state = viewModel.state
    Box {
        ViewPhonePassword(
            isSuccess = state.isSuccess,
            txtWarning = state.txtWarning,
            type = state.type,
            size = state.size,
            input = state.input,
            password = state.password,
            inputChanged = {
                viewModel.enterDigit(it)
            },
            onConfirmSuccess = {
                if (state.type == PasscodeViewType.TYPE_SET_PASSCODE) onBack() else onNavigate(
                    AppRoute.MainGraph.route
                )
            }
        )
    }
}


@Preview
@Composable
private fun PasswordScreenPrev() {
    val viewmodel: PasswordViewModel = hiltViewModel()
    PasswordScreen(viewModel = viewmodel, onBack = {}, onNavigate = { route -> })
}