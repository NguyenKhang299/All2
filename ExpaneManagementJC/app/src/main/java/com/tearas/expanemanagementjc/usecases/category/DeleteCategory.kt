package com.tearas.expanemanagementjc.usecases.category

import com.tearas.expanemanagementjc.data.repository.CategoryRepositoryImpl
import com.tearas.expanemanagementjc.domain.model.Category

class DeleteCategory(private val categoryRepositoryImpl: CategoryRepositoryImpl) {
    suspend operator fun invoke(category: Category) =
        categoryRepositoryImpl.deleteCategory(category)
}