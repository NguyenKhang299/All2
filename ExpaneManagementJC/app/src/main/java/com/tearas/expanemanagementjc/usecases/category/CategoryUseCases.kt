package com.tearas.expanemanagementjc.usecases.category

data class CategoryUseCases(
    val deleteCategory: DeleteCategory,
    val insertCategory: InsertCategory,
    val selectCategoriesDto: SelectCategoriesDto,
    val updateCategory: UpdateCategory,
    val selectCategory: SelectCategory,
    val selectMapCategoriesImage: SelectMapCategoriesImage
)