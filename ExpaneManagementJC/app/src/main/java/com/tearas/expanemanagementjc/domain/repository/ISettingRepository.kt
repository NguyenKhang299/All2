package com.tearas.expanemanagementjc.domain.repository

import com.tearas.expanemanagementjc.domain.model.Account
import com.tearas.expanemanagementjc.domain.model.OptionsSetting
import kotlinx.coroutines.flow.Flow

interface ISettingRepository {
    suspend fun setSetting(setting: OptionsSetting)
    fun getSetting(): Flow<OptionsSetting?>
}