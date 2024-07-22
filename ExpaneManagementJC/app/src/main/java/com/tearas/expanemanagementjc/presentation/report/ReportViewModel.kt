package com.tearas.expanemanagementjc.presentation.report

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tearas.expanemanagementjc.data.mapper.calculateTotalProfitLoss
import com.tearas.expanemanagementjc.usecases.statistcal.StatisticalUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val statisticalUseCases: StatisticalUseCases
) : ViewModel() {
    var state by mutableStateOf(ReportState())
        private set

    fun onEvent(event: ReportEvent) {
        when (event) {
            is ReportEvent.StatisticalReport -> statisticalReport()
        }
    }

    private fun statisticalReport() {
        viewModelScope.launch {
            statisticalUseCases.statisticalReportExpenses().collect {
                state = state.copy(
                    statisticalReport = it,
                    totalProfitLoss = it.values.flatten().calculateTotalProfitLoss()
                )
            }
        }
    }
}