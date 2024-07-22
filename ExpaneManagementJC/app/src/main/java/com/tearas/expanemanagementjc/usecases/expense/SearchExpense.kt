package com.tearas.expanemanagementjc.usecases.expense

import com.tearas.expanemanagementjc.data.repository.ExpenseRepositoryImpl

class SearchExpense(private val expenseRepository: ExpenseRepositoryImpl) {
    suspend operator fun invoke(category: String) = expenseRepository.searchExpenses(category)
}