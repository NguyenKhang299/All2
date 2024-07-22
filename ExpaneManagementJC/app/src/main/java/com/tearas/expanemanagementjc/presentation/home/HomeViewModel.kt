package com.tearas.expanemanagementjc.presentation.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tearas.expanemanagementjc.data.mapper.calculateTotalProfitLoss
import com.tearas.expanemanagementjc.usecases.expense.ExpenseUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val expenseUseCases: ExpenseUseCases) :
    ViewModel() {

    var state by mutableStateOf(HomeState())
        private set

    init {
        initScreenData()
    }
    fun initScreenData() {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
        }
        onEvent(
            HomeEvent.SelectExpenses(
                calendar[Calendar.MONTH] + 1,
                calendar[Calendar.YEAR]
            )
        )
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.SelectExpenses -> selectExpenses(event.month, event.year)
            is HomeEvent.SearchExpenses -> searchExpenses(event.category)
        }
    }

    private fun searchExpenses(category: String) {
        viewModelScope.launch {
            expenseUseCases.searchExpense(category).collect {
                state = state.copy(
                    searchExpenses = it,
                )
            }
        }
    }


    private fun selectExpenses(month: Int, year: Int) {
        viewModelScope.launch {
            expenseUseCases.selectExpenses(month, year).collect {
                val totalProfitLoss = it.keys.toList()
                state = state.copy(
                    mapExpenses = it,
                    searchExpenses = emptyMap(),
                    totalProfitLoss = totalProfitLoss.calculateTotalProfitLoss()
                )
            }
        }
    }
}