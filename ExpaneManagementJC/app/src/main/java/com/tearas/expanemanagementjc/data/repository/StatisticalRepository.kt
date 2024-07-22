package com.tearas.expanemanagementjc.data.repository

import com.tearas.expanemanagementjc.data.dto.StatisticalDto
import com.tearas.expanemanagementjc.data.local.StatisticalDAO
import com.tearas.expanemanagementjc.data.mapper.mapCategories1
import com.tearas.expanemanagementjc.data.mapper.mapToReportExpense
import com.tearas.expanemanagementjc.domain.model.ProfitLoss
import com.tearas.expanemanagementjc.domain.model.TransactionType
import com.tearas.expanemanagementjc.domain.repository.IStatisticalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StatisticalRepositoryImpl(private val statisticalDao: StatisticalDAO) :
    IStatisticalRepository {
    override suspend fun statisticalMonth(
        transactionType: TransactionType,
        month: Int,
        year: Int
    ): List<StatisticalDto> {
        val monthString = month.toString().padStart(2, '0')
        val yearString = year.toString()
        return statisticalDao.statisticalMonth(transactionType, monthString, yearString).mapCategories1()
    }

    override suspend fun statisticalDetailsCategoryByMonth(
        idCategory: Long,
        transactionType: TransactionType,
        month: Int,
        year: Int
    ): List<StatisticalDto> {
        val monthString = month.toString().padStart(2, '0')
        return statisticalDao.statisticalDetailsCategoryByMonth(
            idCategory, transactionType, monthString, year.toString()
        ).mapCategories1()
    }

    override suspend fun statisticalYear(
        transactionType: TransactionType,
        year: Int
    ): List<StatisticalDto> {
        return statisticalDao.statisticalYear(transactionType, year.toString()).mapCategories1()
    }

    override suspend fun statisticalDetailsCategoryByYear(
        idCategory: Long,
        transactionType: TransactionType,
        year: Int
    ): List<StatisticalDto> {
        return statisticalDao.statisticalDetailsCategoryByYear(
            idCategory, transactionType, year.toString()
        ).mapCategories1()
    }

    override fun statisticalReportExpense(): Flow<Map<Int, List<ProfitLoss>>> {
        return statisticalDao.statisticalReportExpenses().map {
            it.mapToReportExpense()
        }
    }
}