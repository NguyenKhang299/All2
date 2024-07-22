package com.tearas.expanemanagementjc.usecases.category_image

import com.tearas.expanemanagementjc.data.repository.CategoryImageRepositoryImpl
import com.tearas.expanemanagementjc.domain.model.CategoryImage

class SelectCategoryImages(private val categoryImageRepositoryImpl: CategoryImageRepositoryImpl) {
    suspend operator fun invoke( ) = categoryImageRepositoryImpl.getCategoryImages()
}