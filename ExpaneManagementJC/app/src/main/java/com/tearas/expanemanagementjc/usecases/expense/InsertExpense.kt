package com.tearas.expanemanagementjc.usecases.expense

import com.tearas.expanemanagementjc.data.repository.ExpenseRepositoryImpl
import com.tearas.expanemanagementjc.domain.model.Expense

class InsertExpense(private val expenseRepositoryImpl: ExpenseRepositoryImpl) {
    suspend operator fun invoke(expense: Expense) = expenseRepositoryImpl.insertExpense(expense)
}