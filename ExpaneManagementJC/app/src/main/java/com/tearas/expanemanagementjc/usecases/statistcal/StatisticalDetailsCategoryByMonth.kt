package com.tearas.expanemanagementjc.usecases.statistcal

import com.tearas.expanemanagementjc.data.repository.StatisticalRepositoryImpl
import com.tearas.expanemanagementjc.domain.model.TransactionType

class StatisticalDetailsCategoryByMonth(private val statisticalRepositoryImpl: StatisticalRepositoryImpl) {
    suspend operator fun invoke(
        idCategory: Long,
        transactionType: TransactionType,
        month: Int,
        year: Int
    ) = statisticalRepositoryImpl.statisticalDetailsCategoryByMonth(
        idCategory,
        transactionType,
        month,
        year
    )
}