package com.tearas.expanemanagementjc.presentation.add_edit_category

sealed class AddEditCategoryEvent {
    data object SelectMapCategoriesImage : AddEditCategoryEvent()
    data object AddCategory : AddEditCategoryEvent()
    data object EditCategory  : AddEditCategoryEvent()
    data class GetCategory(val id: Long) : AddEditCategoryEvent()
}