package com.tearas.expanemanagementjc.presentation.setting

sealed class SettingEvent {
    data object GetSetting : SettingEvent()
    data object SetSetting : SettingEvent()
    data object ExportExcel : SettingEvent()
    data object DeletePass : SettingEvent()
}