package com.tearas.expanemanagementjc.presentation.setting_category

import com.tearas.expanemanagementjc.data.dto.CategoryDto
import com.tearas.expanemanagementjc.domain.model.Category

data class SettingState(
    val categoriesSpend: List<CategoryDto> = emptyList(),
    val categoriesCollect: List<CategoryDto> = emptyList(),
    val category: Category? = null,
    val isSuccess: Boolean? = null
) {
}