package com.tearas.expanemanagementjc.usecases.category_image

data class CategoryImageUseCase(
    val selectedCategoryImage: SelectCategoryImage,
    val selectedCategoryImages: SelectCategoryImages,
    val insertCategoryImage: InsertCategoryImage
) {
}