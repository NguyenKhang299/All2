package com.tearas.expanemanagementjc.data.repository

import com.tearas.expanemanagementjc.domain.model.Account
import com.tearas.expanemanagementjc.data.local.AccountDAO
import kotlinx.coroutines.flow.Flow

class AccountRepositoryImpl(private val accountDAO: AccountDAO) : AccountDAO {

    override suspend fun addAccount(account: Account) {
        accountDAO.addAccount(account)
    }

    override suspend fun deleteAccount(account: Account) {
        accountDAO.deleteAccount(account)
    }

    override suspend fun selectAccount(): Account  {
        return accountDAO.selectAccount()
    }

    override fun selectAccounts(): Flow<List<Account>> {
        return accountDAO.selectAccounts()
    }
}