package com.tearas.expanemanagementjc.presentation.setting_category

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tearas.expanemanagementjc.data.dto.CategoryDto
import com.tearas.expanemanagementjc.domain.model.TransactionType
import com.tearas.expanemanagementjc.usecases.category.CategoryUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingCateViewModel @Inject constructor(
    private val categoryUseCases: CategoryUseCases
) : ViewModel() {

    var state by mutableStateOf(SettingState())

    fun onEvent(event: SettingEvent) {
        when (event) {
            is SettingEvent.GetCategoriesDto -> getCategories(event.transactionType)
            is SettingEvent.UpdateCategory -> updateCategory(event.idCategory)
            is SettingEvent.DeleteCategory -> deleteCategory(event.idCategory)
            is SettingEvent.GetCategory -> {}
        }
    }

    private fun deleteCategory(idCategory: Long) {
        viewModelScope.launch {
            val category = categoryUseCases.selectCategory(idCategory)
            categoryUseCases.deleteCategory(category)
        }
    }

    private fun updateCategory(idCategory: Long) {
        viewModelScope.launch {
            val category = categoryUseCases.selectCategory(idCategory)
            categoryUseCases.updateCategory(category.copy(isDelete = !category.isDelete))
        }
    }

    private fun filterAndSortCategories(categories: List<CategoryDto>): List<CategoryDto> {
        return categories
            .filterNot { !it.isDefault && it.isDelete }
            .sortedWith(compareBy<CategoryDto> { it.isDefault }.thenBy { it.isDelete })
            .toList()
    }

    private fun getCategories(transactionType: TransactionType) {
        viewModelScope.launch(Dispatchers.IO) {
            categoryUseCases.selectCategoriesDto(transactionType).collect {
                val sortedCategories = filterAndSortCategories(it)
                state = if (transactionType == TransactionType.COLLECT) {
                    state.copy(categoriesCollect = sortedCategories)
                } else {
                    state.copy(categoriesSpend = sortedCategories)
                }
            }
        }
    }
}