package com.tearas.expanemanagementjc.presentation.statistical

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tearas.expanemanagementjc.data.mapper.mapToDetailsPieChart
import com.tearas.expanemanagementjc.domain.model.TransactionType
import com.tearas.expanemanagementjc.usecases.statistcal.StatisticalUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class StatisticalViewModel @Inject constructor(
    private val statisticalUseCases: StatisticalUseCases
) : ViewModel() {

    var state by mutableStateOf(StatisticalState())
        private set
    val maxValue get() = state.statisticalDto.sumOf { it.totalExpenses }
    val maxValueDetailsCate get() = state.detailsCategory.sumOf { it.totalExpenses }

    fun onEvent(event: StatisticalEvent) {
        when (event) {
            is StatisticalEvent.StatisticalMonth -> statisticalMonth(
                event.transactionType,
                event.month,
                event.year
            )

            is StatisticalEvent.StatisticalYear -> statisticalYear(
                event.transactionType,
                event.year
            )

            is StatisticalEvent.StatisticalDetailsCategoryByMonth -> statisticalDetailsCategoryByMonth(
                event.idCategory,
                event.transactionType,
                event.month,
                event.year
            )

            is StatisticalEvent.StatisticalDetailsCategoryByYear -> statisticalDetailsCategoryByYear(
                event.idCategory,
                event.transactionType,
                event.year
            )
        }
    }

    private fun statisticalDetailsCategoryByYear(
        idCategory: Long,
        transactionType: TransactionType,
        year: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val statistical = statisticalUseCases.statisticalDetailsCategoryByYear(
                idCategory, transactionType, year
            )
            state = state.copy(
                detailsCategory = statistical,
            )
        }
    }

    private fun statisticalDetailsCategoryByMonth(
        idCategory: Long,
        transactionType: TransactionType,
        month: Int,
        year: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val statistical = statisticalUseCases.statisticalDetailsCategoryByMonth(
                idCategory, transactionType, month, year
            )
            state = state.copy(
                detailsCategory = statistical,
            )
        }
    }

    private fun statisticalYear(transactionType: TransactionType, year: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val statisticalYear = statisticalUseCases.statisticalYear(transactionType, year)
            state = state.copy(
                statisticalDto = statisticalYear,
                detailsPieChart = statisticalYear.mapToDetailsPieChart()
            )
        }
    }

    private fun statisticalMonth(transactionType: TransactionType, month: Int, year: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val statisticalMonth =
                statisticalUseCases.statisticalMonth(transactionType, month, year)
            state = state.copy(
                statisticalDto = statisticalMonth,
                detailsPieChart = statisticalMonth.mapToDetailsPieChart()
            )
        }
    }
}