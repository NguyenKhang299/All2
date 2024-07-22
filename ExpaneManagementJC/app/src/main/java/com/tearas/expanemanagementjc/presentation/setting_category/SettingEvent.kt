package com.tearas.expanemanagementjc.presentation.setting_category

import com.tearas.expanemanagementjc.domain.model.TransactionType

sealed class SettingEvent {
    data class GetCategoriesDto(val transactionType: TransactionType) : SettingEvent()
    data class GetCategory(val idCategory: Long) : SettingEvent()
    data class UpdateCategory(val idCategory: Long) : SettingEvent()
    data class DeleteCategory(val idCategory: Long) : SettingEvent()
}