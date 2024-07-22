package com.tearas.expanemanagementjc.presentation.add_edit_expense

import com.tearas.expanemanagementjc.data.dto.ExpenseDto
import com.tearas.expanemanagementjc.domain.model.Expense
import com.tearas.expanemanagementjc.domain.model.TransactionType

data class StateAddExpense(
     val expense: Expense? = null,
    val expenseDto: ExpenseDto? = null,
    val isSuccess: Boolean? = null
) {
}