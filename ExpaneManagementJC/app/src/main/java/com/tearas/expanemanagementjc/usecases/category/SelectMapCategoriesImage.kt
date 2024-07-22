package com.tearas.expanemanagementjc.usecases.category

import com.tearas.expanemanagementjc.data.repository.CategoryRepositoryImpl
import com.tearas.expanemanagementjc.domain.model.Category

class SelectMapCategoriesImage(private val categoryRepositoryImpl: CategoryRepositoryImpl) {
    suspend operator fun invoke() =
        categoryRepositoryImpl.selectMapCategoriesImage()
}