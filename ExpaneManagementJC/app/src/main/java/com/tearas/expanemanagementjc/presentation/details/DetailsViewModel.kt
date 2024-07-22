package com.tearas.expanemanagementjc.presentation.details

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tearas.expanemanagementjc.data.dto.ExpenseDto
import com.tearas.expanemanagementjc.domain.model.Expense
import com.tearas.expanemanagementjc.usecases.expense.ExpenseUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val expenseUseCases: ExpenseUseCases) :
    ViewModel() {
    var detailsState by mutableStateOf(DetailsState())


    fun onEvent(event: DetailsEvent) {
        when (event) {
            is DetailsEvent.GetDetails -> getDetails(event.id)
            is DetailsEvent.Delete -> deleteExpense(event.id)
        }
    }

    private fun deleteExpense(id: Long) {
        viewModelScope.launch {
            expenseUseCases.deleteExpense(id)
            detailsState = detailsState.copy(isDeleted = true)
        }
    }

    private fun getDetails(id: Long) {
        viewModelScope.launch {
            val expense = expenseUseCases.selectExpense(id)
            detailsState = detailsState.copy(expenseDto = expense)
        }
    }
}