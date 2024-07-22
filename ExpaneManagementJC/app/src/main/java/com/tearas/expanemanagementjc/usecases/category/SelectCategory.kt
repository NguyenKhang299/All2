package com.tearas.expanemanagementjc.usecases.category

import com.tearas.expanemanagementjc.data.repository.CategoryRepositoryImpl
import com.tearas.expanemanagementjc.domain.model.TransactionType

class SelectCategory(private val categoryRepositoryImpl: CategoryRepositoryImpl) {
    suspend operator fun invoke(id: Long) = categoryRepositoryImpl.selectCategory(id)
}