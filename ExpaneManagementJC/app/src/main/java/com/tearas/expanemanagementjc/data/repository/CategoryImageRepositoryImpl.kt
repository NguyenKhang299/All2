package com.tearas.expanemanagementjc.data.repository

import com.tearas.expanemanagementjc.data.local.CategoryImageDAO
import com.tearas.expanemanagementjc.domain.model.CategoryImage
import com.tearas.expanemanagementjc.domain.repository.ICategoryImageRepository

class CategoryImageRepositoryImpl(private val categoryImageDAO: CategoryImageDAO) :
    ICategoryImageRepository {
    override suspend fun insertCategoryImage(vararg categoryImage: CategoryImage): List<Long> {
        return categoryImageDAO.insertCategoryImg(*categoryImage)
    }

    override suspend fun getCategoryImage(id: Long): CategoryImage {
        return categoryImageDAO.selectCategoryImage(id)
    }

    override suspend fun getCategoryImages(): List<CategoryImage> {
        return categoryImageDAO.selectCategoriesImg()
    }
}