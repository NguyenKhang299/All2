package com.tearas.expanemanagementjc.usecases.expense

import com.tearas.expanemanagementjc.data.repository.ExpenseRepositoryImpl

class SelectExpense(private val expenseRepository: ExpenseRepositoryImpl) {
    suspend operator fun invoke(expenseId: Long) = expenseRepository.selectExpense(expenseId)
}