package com.tearas.expanemanagementjc.usecases.category

import com.tearas.expanemanagementjc.data.repository.CategoryRepositoryImpl
import com.tearas.expanemanagementjc.domain.model.TransactionType

class SelectCategoriesDto(private val categoryRepositoryImpl: CategoryRepositoryImpl) {
    operator fun invoke(type: TransactionType) = categoryRepositoryImpl.selectCategoriesDto(type)
}