package com.tearas.expanemanagementjc.usecases.statistcal

import com.tearas.expanemanagementjc.data.repository.StatisticalRepositoryImpl
import com.tearas.expanemanagementjc.domain.model.TransactionType

class StatisticalYear(private val statisticalRepositoryImpl: StatisticalRepositoryImpl) {
    suspend operator fun invoke(
        transactionType: TransactionType,
        year: Int
    ) = statisticalRepositoryImpl.statisticalYear(transactionType, year)
}