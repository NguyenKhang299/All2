package com.tearas.expanemanagementjc.usecases.manager_user

import com.tearas.expanemanagementjc.data.manager.LocalUserManager

class DeletePassword(private val localUserManager: LocalUserManager) {
    suspend operator fun invoke() = localUserManager.deletePassword()
}