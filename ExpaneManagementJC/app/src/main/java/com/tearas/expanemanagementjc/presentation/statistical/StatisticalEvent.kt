package com.tearas.expanemanagementjc.presentation.statistical

import com.tearas.expanemanagementjc.domain.model.TransactionType

sealed class StatisticalEvent {
    data class StatisticalMonth(
        val transactionType: TransactionType,
        val month: Int,
        val year: Int
    ) : StatisticalEvent()

    data class StatisticalYear(
        val transactionType: TransactionType,
        val year: Int
    ) : StatisticalEvent()

    data class StatisticalDetailsCategoryByMonth(
        val idCategory: Long,
        val transactionType: TransactionType,
        val month: Int,
        val year: Int
    ) : StatisticalEvent()

    data class StatisticalDetailsCategoryByYear(
        val idCategory: Long,
        val transactionType: TransactionType,
        val year: Int
    ) : StatisticalEvent()

}
