package com.tearas.expanemanagementjc.presentation.password

sealed class PasswordEvent {
    data object GetPassword : PasswordEvent()
    data object DeletePassword : PasswordEvent()
    data object UpdatePassword : PasswordEvent()
    data object CheckPassword : PasswordEvent()
    data object SetPassword : PasswordEvent()
}