package com.tearas.expanemanagementjc.presentation.report

import com.tearas.expanemanagementjc.domain.model.ProfitLoss

data class ReportState(
    val statisticalReport: Map<Int, List<ProfitLoss>> = mapOf(),
    val totalProfitLoss: ProfitLoss = ProfitLoss()
)
