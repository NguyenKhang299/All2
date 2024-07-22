package com.tearas.expanemanagementjc.domain.repository

import com.tearas.expanemanagementjc.data.dto.StatisticalDto
import com.tearas.expanemanagementjc.domain.model.ProfitLoss
import com.tearas.expanemanagementjc.domain.model.TransactionType
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface IStatisticalRepository {
    suspend fun statisticalMonth(
        transactionType: TransactionType,
        month: Int,
        year: Int
    ): List<StatisticalDto>

    suspend fun statisticalDetailsCategoryByMonth(
        idCategory: Long,
        transactionType: TransactionType,
        month: Int,
        year: Int
    ): List<StatisticalDto>

    suspend fun statisticalYear(
        transactionType: TransactionType,
        year: Int
    ): List<StatisticalDto>

    suspend fun statisticalDetailsCategoryByYear(
        idCategory: Long,
        transactionType: TransactionType,
        year: Int
    ): List<StatisticalDto>

    fun statisticalReportExpense(): Flow<Map<Int, List<ProfitLoss>>>
}