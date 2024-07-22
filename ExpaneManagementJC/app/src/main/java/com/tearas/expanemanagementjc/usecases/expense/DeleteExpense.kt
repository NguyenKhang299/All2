package com.tearas.expanemanagementjc.usecases.expense

import com.tearas.expanemanagementjc.data.repository.ExpenseRepositoryImpl
import com.tearas.expanemanagementjc.domain.model.Expense

class DeleteExpense(private val expenseRepositoryImpl: ExpenseRepositoryImpl) {
    suspend operator fun invoke(expenseId: Long) = expenseRepositoryImpl.deleteExpense(expenseId)
}