package com.tearas.expanemanagementjc.presentation.statistical

import com.tearas.expanemanagementjc.data.dto.StatisticalDto

data class StatisticalState(
    val statisticalDto: List<StatisticalDto> = emptyList(),
    val detailsPieChart: Map<String, Double> = mapOf(),
    val detailsCategory: List<StatisticalDto> = emptyList(),
) {
}