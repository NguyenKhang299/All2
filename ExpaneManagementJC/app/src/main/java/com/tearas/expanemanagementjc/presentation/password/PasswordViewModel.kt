package com.tearas.expanemanagementjc.presentation.password

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tearas.expanemanagementjc.R
import com.tearas.expanemanagementjc.presentation.password.component.PasscodeViewType
import com.tearas.expanemanagementjc.usecases.manager_user.LocalUserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val localUserUseCases: LocalUserUseCases
) : ViewModel() {
    val enter_pass = context.getString(R.string.enter_pass)

    var state by mutableStateOf(PasswordState(txtWarning = enter_pass))
        private set

    fun setType(type: PasscodeViewType) {
        state = state.copy(type = type)
    }

    // State variables
    val firstInputTip = context.getString(R.string.first_input_tip, state.size)
    val secondInputTip = context.getString(R.string.second_input_tip)
    val wrongLengthTip = context.getString(R.string.wrong_length_tip, state.size)
    val wrongInputTip = context.getString(R.string.wrong_input_tip)
    val correctInputTip = context.getString(R.string.correct_input_tip)


    private fun isPasswordCorrect(): Boolean = state.password == state.input

    init {
        onEvent(PasswordEvent.GetPassword)
    }

    fun onEvent(event: PasswordEvent) {
        when (event) {
            is PasswordEvent.GetPassword -> getPassword()
            is PasswordEvent.DeletePassword -> deletePassword()
            is PasswordEvent.SetPassword -> addPassword()
            else -> {}
        }
    }

    var check = false
    fun enterDigit(digit: String) {
        if (!check) {
            if (state.input.length < state.size) {
                state = state.copy(input = state.input + digit)
                setPass(digit)
            }
            if (state.input.length == state.size) {
                check = true
                handleResult()
                viewModelScope.launch {
                    delay(200)
                    clearInput()
                    check = false
                }
                return
            }
        }
    }

    // Private methods
    private fun clearInput() {
        state = state.copy(input = "")
    }

    private fun setPass(key: String) {
        if (state.type == PasscodeViewType.TYPE_SET_PASSCODE) {
            state = if (state.isFirst)
                state.copy(passwordFirst = state.passwordFirst + key)
            else
                state.copy(passwordSecond = state.passwordSecond + key)
        }
    }

    private fun handleSetPasscode(): String? {
        return if (state.isFirst) {
            state = state.copy(isFirst = false)
            secondInputTip
        } else {
            if ((state.action == PasswordEvent.CheckPassword && isPasswordCorrect()) ||
                (state.action == PasswordEvent.SetPassword && state.passwordFirst == state.passwordSecond)
            ) {
                onEvent(state.action)
                state = state.copy(isConfirmed = true)
                correctInputTip
            } else {
                state = state.copy(passwordSecond = "")
                null
            }
        }
    }

    private fun handleCheckPasscode(): String? {
        return if (state.input == state.password) {
            onEvent(state.action)
            addPassword()
            state = state.copy(isConfirmed = true)
            correctInputTip
        } else {
            null
        }
    }

    private fun handleResult() {
        val successMessage = when (state.type) {
            PasscodeViewType.TYPE_CHECK_PASSCODE -> handleCheckPasscode()
            PasscodeViewType.TYPE_SET_PASSCODE -> handleSetPasscode()
        }

        state = state.copy(
            txtWarning = successMessage ?: wrongInputTip,
            isConfirmed = state.isConfirmed
        )
    }

    private fun getPassword() {
        viewModelScope.launch {
            localUserUseCases.getPassword().collect { passwordFromDb ->
                state = state.copy(password = passwordFromDb ?: "")
            }
        }
    }

    private fun addPassword() {
        viewModelScope.launch {
            localUserUseCases.setPassword(state.input)
            state = state.copy(isSuccess = true)
        }
    }

    private fun deletePassword() {
        viewModelScope.launch {
            localUserUseCases.deletePassword()
            state = state.copy(isSuccess = true)
        }
    }

    private fun updatePassword() {
        viewModelScope.launch {
            if (isPasswordCorrect()) {
                localUserUseCases.setPassword(state.input)
                state = state.copy(isSuccess = true)
            }
        }
    }
}
