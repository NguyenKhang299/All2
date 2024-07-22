package com.tearas.expanemanagementjc.usecases.category_image

import com.tearas.expanemanagementjc.data.repository.CategoryImageRepositoryImpl
import com.tearas.expanemanagementjc.domain.model.CategoryImage

class InsertCategoryImage (private val categoryImageRepositoryImpl: CategoryImageRepositoryImpl) {
    suspend operator fun invoke(vararg categoryImage: CategoryImage) = categoryImageRepositoryImpl.insertCategoryImage(*categoryImage)
}