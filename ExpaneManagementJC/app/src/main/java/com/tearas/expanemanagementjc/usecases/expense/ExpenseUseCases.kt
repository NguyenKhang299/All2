package com.tearas.expanemanagementjc.usecases.expense

data class ExpenseUseCases(
    val selectExpenses: SelectExpenses,
    val selectExpense: SelectExpense,
    val insertExpense: InsertExpense,
    val deleteExpense: DeleteExpense,
    val updateExpense: UpdateExpense,
    val searchExpense: SearchExpense
) {

}