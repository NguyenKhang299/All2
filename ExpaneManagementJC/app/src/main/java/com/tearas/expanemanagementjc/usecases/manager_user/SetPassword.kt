package com.tearas.expanemanagementjc.usecases.manager_user

import com.tearas.expanemanagementjc.data.manager.LocalUserManager

class SetPassword(private val localUserManager: LocalUserManager) {
    suspend operator fun invoke(password: String) = localUserManager.setPassword(password)
}