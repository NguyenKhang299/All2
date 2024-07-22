package com.tearas.expanemanagementjc.data.dto

import com.tearas.expanemanagementjc.domain.model.Expense
import com.tearas.expanemanagementjc.domain.model.ProfitLoss

data class ItemExpenseDto(val profitLoss: ProfitLoss, val expense: List<ExpenseDto>)
