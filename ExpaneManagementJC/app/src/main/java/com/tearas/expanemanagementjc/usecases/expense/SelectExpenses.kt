package com.tearas.expanemanagementjc.usecases.expense

import com.tearas.expanemanagementjc.data.repository.ExpenseRepositoryImpl

class SelectExpenses(private val expenseRepositoryImpl: ExpenseRepositoryImpl) {
    suspend operator fun invoke(month: Int, year: Int) =
        expenseRepositoryImpl.selectExpenses(month, year)
}