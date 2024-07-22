package com.tearas.expanemanagementjc.domain.repository

import com.tearas.expanemanagementjc.domain.model.Account
import kotlinx.coroutines.flow.Flow

interface ILocalUserManager {
    suspend fun setAccountUsing(account: Account)
    fun getAccountUsing(): Flow<Account?>
    fun getPassword(): Flow<String?>
    suspend fun setPassword(password: String)
    suspend fun deletePassword()
}