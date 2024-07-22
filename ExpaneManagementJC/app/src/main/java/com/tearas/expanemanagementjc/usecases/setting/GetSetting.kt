package com.tearas.expanemanagementjc.usecases.setting

import com.tearas.expanemanagementjc.data.manager.SettingManagerImpl
import com.tearas.expanemanagementjc.domain.model.OptionsSetting

class GetSetting(private val settingManagerImpl: SettingManagerImpl) {
    operator fun invoke() = settingManagerImpl.getSetting()
}