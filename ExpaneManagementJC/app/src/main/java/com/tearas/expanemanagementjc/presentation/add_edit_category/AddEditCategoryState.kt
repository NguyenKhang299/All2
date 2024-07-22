package com.tearas.expanemanagementjc.presentation.add_edit_category

import com.tearas.expanemanagementjc.domain.model.Category
import com.tearas.expanemanagementjc.domain.model.CategoryImage

data class AddEditCategoryState(
    val categoriesImage: Map<String, List<CategoryImage>> = emptyMap(),
    val category: Category? = null,
    val isSuccess: Boolean? = null
) {
}