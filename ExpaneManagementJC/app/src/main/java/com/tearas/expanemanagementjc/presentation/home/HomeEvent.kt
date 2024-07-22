package com.tearas.expanemanagementjc.presentation.home

sealed class HomeEvent {
    data class SelectExpenses(val month: Int, val year: Int) : HomeEvent()
    data class SearchExpenses(val category: String) : HomeEvent()
}