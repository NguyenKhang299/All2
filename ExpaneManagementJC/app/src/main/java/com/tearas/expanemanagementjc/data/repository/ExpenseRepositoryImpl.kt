package com.tearas.expanemanagementjc.data.repository

import android.annotation.SuppressLint
import android.util.Log
import com.tearas.expanemanagementjc.data.dto.ExpenseDto
import com.tearas.expanemanagementjc.data.local.ExpenseDAO
import com.tearas.expanemanagementjc.data.mapper.mapToProfitLoss
import com.tearas.expanemanagementjc.domain.model.Expense
import com.tearas.expanemanagementjc.domain.model.ProfitLoss
import com.tearas.expanemanagementjc.domain.repository.IExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Date

class ExpenseRepositoryImpl(private val expenseDAO: ExpenseDAO) : IExpenseRepository {
    override suspend fun insertExpense(expense: Expense): Long {
         return expenseDAO.insertExpense(expense)
    }

    override suspend fun updateExpense(vararg expense: Expense) {
        expenseDAO.updateExpense(*expense)
    }

    override suspend fun deleteExpense(id: Long) {
        expenseDAO.deleteExpense(id)
    }

    @SuppressLint("SimpleDateFormat")
    override suspend fun selectExpenses(
        month: Int,
        year: Int
    ): Flow<Map<ProfitLoss, List<ExpenseDto>>> {
        val monthString = month.toString().padStart(2, '0')
        val yearString = year.toString()
        return expenseDAO.selectExpenses(monthString, yearString).map { expenseList ->
            expenseList.mapToProfitLoss()
        }
    }

    override suspend fun searchExpenses(category: String): Flow<Map<ProfitLoss, List<ExpenseDto>>> {
        return expenseDAO.searchExpenses(category).map { expenseList ->
            expenseList.mapToProfitLoss()
        }
    }


    override suspend fun selectExpense(id: Long): ExpenseDto {
        return expenseDAO.selectExpense(id)
    }
}


