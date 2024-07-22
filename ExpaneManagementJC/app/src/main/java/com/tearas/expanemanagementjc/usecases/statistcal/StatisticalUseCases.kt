package com.tearas.expanemanagementjc.usecases.statistcal

data class StatisticalUseCases(
    val statisticalMonth: StatisticalMonth,
    val statisticalYear: StatisticalYear,
    val statisticalDetailsCategoryByMonth: StatisticalDetailsCategoryByMonth,
    val statisticalDetailsCategoryByYear: StatisticalDetailsCategoryByYear,
    val statisticalReportExpenses: StatisticalReportExpenses
) {
}