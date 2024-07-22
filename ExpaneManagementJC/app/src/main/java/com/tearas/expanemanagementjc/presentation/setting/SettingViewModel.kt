package com.tearas.expanemanagementjc.presentation.setting

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tearas.expanemanagementjc.ExpenseApplication
import com.tearas.expanemanagementjc.data.mapper.calculateTotalProfitLoss
import com.tearas.expanemanagementjc.domain.model.CurrencyFormat
import com.tearas.expanemanagementjc.domain.model.Language
import com.tearas.expanemanagementjc.domain.model.OptionsSetting
import com.tearas.expanemanagementjc.domain.model.ThemeMode
import com.tearas.expanemanagementjc.domain.model.TimeFormat
import com.tearas.expanemanagementjc.usecases.excel.ExcelUseCases
import com.tearas.expanemanagementjc.usecases.manager_user.LocalUserUseCases
import com.tearas.expanemanagementjc.usecases.setting.SettingUseCases
import com.tearas.expanemanagementjc.usecases.statistcal.StatisticalUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val settingUseCases: SettingUseCases,
    private val excelUseCases: ExcelUseCases,
    private val statisticalUseCases: StatisticalUseCases,
    private val localUserUseCases: LocalUserUseCases
) : ViewModel() {
    var settingOptions by mutableStateOf(OptionsSetting())
    var path by mutableStateOf<String?>(null)

    init {
        onEvent(SettingEvent.GetSetting)
    }

    fun onEvent(event: SettingEvent) {
        when (event) {
            is SettingEvent.GetSetting -> getSetting()
            is SettingEvent.SetSetting -> setSetting()
            is SettingEvent.ExportExcel -> exportExcel()
            is SettingEvent.DeletePass -> deletePass()
        }
    }

    private fun deletePass() {
        viewModelScope.launch {
            localUserUseCases.deletePassword()
            ExpenseApplication.isPasscode = false
        }
    }

    private fun exportExcel() {
        viewModelScope.launch(Dispatchers.IO) {
            statisticalUseCases.statisticalReportExpenses().collect {
                path = excelUseCases.exportExcel(it)
            }
        }
    }

    private fun setSetting() {
        viewModelScope.launch {
            settingUseCases.setSetting(settingOptions)
        }
    }

    private fun getSetting() {
        viewModelScope.launch {
            settingUseCases.getSetting().collect {
                if (it != null) {
                    settingOptions = it
                }
            }
        }
    }

    fun updateCurrencyFormat(currency: CurrencyFormat) {
        settingOptions = settingOptions.copy(currencyFormat = currency)
        setSetting()
    }

    fun updateTimeFormat(formatTimeFormat: TimeFormat) {
        settingOptions = settingOptions.copy(timeFormat = formatTimeFormat)
        setSetting()
    }

    fun updateLang(lang: Language) {
        settingOptions = settingOptions.copy(language = lang)
        setSetting()
    }

    fun updateThemeMode(themeMode: ThemeMode) {
        settingOptions = settingOptions.copy(themeMode = themeMode)
        setSetting()
    }
}