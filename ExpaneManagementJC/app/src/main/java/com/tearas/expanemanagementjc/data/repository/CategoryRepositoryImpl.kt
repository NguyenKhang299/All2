package com.tearas.expanemanagementjc.data.repository

import com.tearas.expanemanagementjc.data.dto.CategoryDto
import com.tearas.expanemanagementjc.data.local.CategoryDAO
import com.tearas.expanemanagementjc.data.mapper.mapCategories
import com.tearas.expanemanagementjc.data.mapper.mapCategories1
import com.tearas.expanemanagementjc.data.mapper.mapToCategoriesImage
import com.tearas.expanemanagementjc.domain.model.Category
import com.tearas.expanemanagementjc.domain.model.CategoryImage
import com.tearas.expanemanagementjc.domain.model.TransactionType
import com.tearas.expanemanagementjc.domain.repository.ICategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CategoryRepositoryImpl(private val categoryDAO: CategoryDAO) : ICategoryRepository {
    override suspend fun insertCategory(vararg category: Category): List<Long> {
        return categoryDAO.insertCategory(*category)
    }

    override suspend fun updateCategory(category: Category): Int {
        return categoryDAO.updateCategory(category)
    }

    override suspend fun selectCategory(idCategory: Long): Category {
        return categoryDAO.selectCategory(idCategory)
    }

    override suspend fun deleteCategory(category: Category) {
        categoryDAO.deleteCategory(category)
    }

    override fun selectCategoriesDto(type: TransactionType): Flow<List<CategoryDto>> {
        return categoryDAO.selectCategoriesDto(type).map { categoryList ->
            categoryList.mapCategories()
        }
    }

    override fun selectAllCategoriesDto(): Flow<List<CategoryDto>> {
        return categoryDAO.selectAllCategoriesDto().map { categoryList ->
            categoryList.mapCategories()
        }
    }

    override fun selectMapCategoriesImage(): Flow<Map<String, List<CategoryImage>>> {
        return categoryDAO.selectMapCategoriesImage().map {
            it.mapToCategoriesImage()
        }
    }
}