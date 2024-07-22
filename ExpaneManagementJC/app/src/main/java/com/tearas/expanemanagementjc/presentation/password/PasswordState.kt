package com.tearas.expanemanagementjc.presentation.password

import com.tearas.expanemanagementjc.presentation.password.component.PasscodeViewType

data class PasswordState(
    val txtWarning: String = "",
    val size: Int = 6,
    val input: String = "",
    val isFirst: Boolean = true,
    val isConfirmed: Boolean = true,
    val type: PasscodeViewType = PasscodeViewType.TYPE_SET_PASSCODE,
    val isSuccess: Boolean = false,
    val password: String = "",
    val passwordFirst: String = "",
    val passwordSecond: String = "",
    val action: PasswordEvent = PasswordEvent.SetPassword
)

