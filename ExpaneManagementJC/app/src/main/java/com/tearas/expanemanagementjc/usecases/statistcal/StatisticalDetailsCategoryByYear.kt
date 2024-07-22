package com.tearas.expanemanagementjc.usecases.statistcal

import com.tearas.expanemanagementjc.data.repository.StatisticalRepositoryImpl
import com.tearas.expanemanagementjc.domain.model.TransactionType

class StatisticalDetailsCategoryByYear(private val statisticalRepositoryImpl: StatisticalRepositoryImpl) {
    suspend operator fun invoke(
        idCategory: Long,
        transactionType: TransactionType,
        year: Int
    ) = statisticalRepositoryImpl.statisticalDetailsCategoryByYear(
        idCategory,
        transactionType,
        year
    )
}