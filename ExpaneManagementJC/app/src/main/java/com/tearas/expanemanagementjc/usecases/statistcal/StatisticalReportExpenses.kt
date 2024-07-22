package com.tearas.expanemanagementjc.usecases.statistcal

import com.tearas.expanemanagementjc.data.repository.StatisticalRepositoryImpl

class StatisticalReportExpenses(private val statisticalRepositoryImpl: StatisticalRepositoryImpl) {
    operator fun invoke() = statisticalRepositoryImpl.statisticalReportExpense()
}