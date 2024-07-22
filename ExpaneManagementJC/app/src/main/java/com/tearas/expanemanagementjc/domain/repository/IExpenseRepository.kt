package com.tearas.expanemanagementjc.domain.repository

import com.tearas.expanemanagementjc.data.dto.ExpenseDto
import com.tearas.expanemanagementjc.domain.model.Expense
import com.tearas.expanemanagementjc.domain.model.ProfitLoss
import kotlinx.coroutines.flow.Flow

interface IExpenseRepository {
    suspend fun insertExpense(expense: Expense): Long

    suspend fun updateExpense(vararg expense: Expense)

    suspend fun deleteExpense(expenseId: Long)

    suspend fun selectExpenses(month: Int, year: Int): Flow<Map<ProfitLoss, List<ExpenseDto>>>

    suspend fun searchExpenses(category: String): Flow<Map<ProfitLoss, List<ExpenseDto>>>

    suspend fun selectExpense(id: Long): ExpenseDto
}