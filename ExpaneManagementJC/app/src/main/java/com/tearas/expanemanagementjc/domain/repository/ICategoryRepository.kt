package com.tearas.expanemanagementjc.domain.repository

import com.tearas.expanemanagementjc.data.dto.CategoryDto
import com.tearas.expanemanagementjc.domain.model.Category
import com.tearas.expanemanagementjc.domain.model.CategoryImage
import com.tearas.expanemanagementjc.domain.model.TransactionType
import kotlinx.coroutines.flow.Flow

interface ICategoryRepository {
    suspend fun insertCategory(vararg category: Category): List<Long>
    suspend fun updateCategory(category: Category): Int
    suspend fun selectCategory(idCategory: Long): Category
    suspend fun deleteCategory(category: Category)
    fun selectCategoriesDto(type: TransactionType): Flow<List<CategoryDto>>
    fun selectAllCategoriesDto(): Flow<List<CategoryDto>>
    fun selectMapCategoriesImage(): Flow<Map<String, List<CategoryImage>>>
}