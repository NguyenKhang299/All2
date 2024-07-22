package com.tearas.expanemanagementjc.presentation.home

import com.tearas.expanemanagementjc.data.dto.ExpenseDto
import com.tearas.expanemanagementjc.domain.model.ProfitLoss

data class HomeState(
    val mapExpenses: Map<ProfitLoss, List<ExpenseDto>> = emptyMap(),
    val searchExpenses: Map<ProfitLoss, List<ExpenseDto>> = emptyMap(),
    val totalProfitLoss: ProfitLoss = ProfitLoss()
) {
}