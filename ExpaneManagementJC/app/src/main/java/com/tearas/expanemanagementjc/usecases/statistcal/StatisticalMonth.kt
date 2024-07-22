package com.tearas.expanemanagementjc.usecases.statistcal

import com.tearas.expanemanagementjc.data.repository.StatisticalRepositoryImpl
import com.tearas.expanemanagementjc.domain.model.TransactionType

class StatisticalMonth (private val statisticalRepositoryImpl: StatisticalRepositoryImpl) {
    suspend operator fun invoke(
        transactionType: TransactionType,
        month: Int,
        year: Int
    ) = statisticalRepositoryImpl.statisticalMonth(transactionType, month, year)
}