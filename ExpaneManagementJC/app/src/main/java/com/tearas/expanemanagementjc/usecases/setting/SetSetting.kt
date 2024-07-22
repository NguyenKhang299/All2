package com.tearas.expanemanagementjc.usecases.setting

import com.tearas.expanemanagementjc.data.manager.SettingManagerImpl
import com.tearas.expanemanagementjc.domain.model.OptionsSetting

class SetSetting(private val settingManagerImpl: SettingManagerImpl) {
    suspend operator fun invoke(setting: OptionsSetting) = settingManagerImpl.setSetting(setting)
}