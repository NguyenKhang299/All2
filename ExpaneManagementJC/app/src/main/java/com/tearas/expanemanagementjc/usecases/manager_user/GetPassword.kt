package com.tearas.expanemanagementjc.usecases.manager_user

import com.tearas.expanemanagementjc.data.manager.LocalUserManager

class GetPassword(private val localUserManager: LocalUserManager) {
    operator fun invoke() = localUserManager.getPassword()
}