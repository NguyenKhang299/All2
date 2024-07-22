package com.tearas.expanemanagementjc.domain.repository

import com.tearas.expanemanagementjc.domain.model.CategoryImage

interface ICategoryImageRepository {
    suspend fun insertCategoryImage(vararg categoryImage: CategoryImage):List<Long>
    suspend fun getCategoryImage(id: Long): CategoryImage
    suspend fun getCategoryImages(): List<CategoryImage>
}