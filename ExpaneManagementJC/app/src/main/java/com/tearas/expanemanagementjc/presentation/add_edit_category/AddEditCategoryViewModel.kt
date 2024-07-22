package com.tearas.expanemanagementjc.presentation.add_edit_category

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tearas.expanemanagementjc.domain.model.Category
import com.tearas.expanemanagementjc.domain.model.CategoryImage
import com.tearas.expanemanagementjc.domain.model.TransactionType
import com.tearas.expanemanagementjc.usecases.category.CategoryUseCases
import com.tearas.expanemanagementjc.usecases.category_image.CategoryImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditCategoryViewModel @Inject constructor(
    private val categoryUseCases: CategoryUseCases,
    private val categoryImageUseCases: CategoryImageUseCase

) : ViewModel() {
    var state by mutableStateOf(AddEditCategoryState())
        private set
    var categoryName by mutableStateOf("")
        private set
    var categoryImgSelected by mutableStateOf<CategoryImage?>(null)
        private set
    var transactionType by mutableStateOf(TransactionType.SPEND)
        private set
    val isCategoryValid get() = categoryName.isNotEmpty() && categoryImgSelected != null

    fun updateTransactionType(transactionType: TransactionType) {
        this.transactionType = transactionType
    }

    fun updateCategoryImage(categoryImage: CategoryImage) {
        categoryImgSelected = categoryImage
    }

    fun updateCategoryName(name: String) {
        categoryName = name
    }

    init {
        onEvent(AddEditCategoryEvent.SelectMapCategoriesImage)
    }

    fun onEvent(event: AddEditCategoryEvent) {
        when (event) {
            is AddEditCategoryEvent.SelectMapCategoriesImage -> getMapCategoriesImage()
            is AddEditCategoryEvent.AddCategory -> addCategory()
            is AddEditCategoryEvent.EditCategory -> updateCategory()
            is AddEditCategoryEvent.GetCategory -> getCategory(event.id)
        }
    }

    private fun updateCategory() {
        addUpdateCategory(true)
    }

    private fun addCategory() {
        addUpdateCategory(false)
    }

    private fun getCategory(id: Long) {
        viewModelScope.launch {
            val category = categoryUseCases.selectCategory(id)
            categoryImgSelected =
                categoryImageUseCases.selectedCategoryImage(category.categoryImageId)
            categoryName = category.category
            transactionType = category.transactionType

            state = state.copy(category = category)
        }
    }

    private fun addUpdateCategory(isUpdateCategory: Boolean) {
        viewModelScope.launch {
            val category = Category(
                state.category?.idCategory ?: 0,
                categoryImgSelected!!.idCategoryImage,
                transactionType,
                categoryImgSelected!!.type,
                categoryName,
                false, false
            )
            if (isUpdateCategory) categoryUseCases.updateCategory(category)
            else categoryUseCases.insertCategory(category)
            state = state.copy(category = category, isSuccess = true)
        }
    }

    private fun getMapCategoriesImage() {
        viewModelScope.launch {
            categoryUseCases.selectMapCategoriesImage().collect {
                state = state.copy(categoriesImage = it)
            }
        }
    }
}