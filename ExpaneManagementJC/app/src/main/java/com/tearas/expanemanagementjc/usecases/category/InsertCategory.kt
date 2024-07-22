package com.tearas.expanemanagementjc.usecases.category

import com.tearas.expanemanagementjc.data.repository.CategoryRepositoryImpl
import com.tearas.expanemanagementjc.domain.model.Category

class InsertCategory(private val categoryRepositoryImpl: CategoryRepositoryImpl) {
    suspend operator fun invoke(vararg category:Category)=categoryRepositoryImpl.insertCategory(*category)
}