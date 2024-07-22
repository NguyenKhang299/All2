package com.tearas.expanemanagementjc.presentation.add_edit_expense

sealed class EventAddExpense {
    data object SelectCategoriesDtoCollect : EventAddExpense()
    data object SelectCategoriesDtoSpend : EventAddExpense()
    data object AddExpense : EventAddExpense()
    data class GetExpense(val idExpense: Long) : EventAddExpense()
    data object UpdateExpense : EventAddExpense()
}