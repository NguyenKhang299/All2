package com.tearas.expanemanagementjc.usecases.account

import com.tearas.expanemanagementjc.data.repository.AccountRepositoryImpl
import com.tearas.expanemanagementjc.data.repository.CategoryRepositoryImpl
import com.tearas.expanemanagementjc.domain.model.Account

class SelectAccountUsing(private val accountRepositoryImpl: AccountRepositoryImpl) {
    suspend operator fun invoke() = accountRepositoryImpl.selectAccount()
}