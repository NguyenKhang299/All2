package com.tearas.expanemanagementjc.domain.model

val nameVnCategories = mapOf(
    CategoryType.ENTERTAINMENT to "Entertainment",
    CategoryType.FOOD to "Food",
    CategoryType.PERSONAL to "Personal",
    CategoryType.EDUCATION to "Education",
    CategoryType.FESTIVAL to "Festival",
    CategoryType.SPORTS to "Sports",
    CategoryType.OFFICE to "Office",
    CategoryType.TRANSPORTATION to "Transportation",
    CategoryType.HEALTH to "Health",
    CategoryType.OTHER to "Other"
)

val nameEnCategories = mapOf(
    CategoryType.ENTERTAINMENT to "Giải trí",
    CategoryType.FOOD to "Ăn uống",
    CategoryType.PERSONAL to "Cá nhân",
    CategoryType.EDUCATION to "Giáo dục",
    CategoryType.FESTIVAL to "Lễ hội",
    CategoryType.SPORTS to "Thể thao",
    CategoryType.OFFICE to "Văn phòng",
    CategoryType.TRANSPORTATION to "Giao thông",
    CategoryType.HEALTH to "Sức khỏe",
    CategoryType.OTHER to "Khác"
)

enum class CategoryType {
    ENTERTAINMENT,
    FOOD,
    SHOPPING,
    PERSONAL,
    EDUCATION,
    FESTIVAL,
    SPORTS,
    OFFICE,
    TRANSPORTATION,
    HEALTH,
    OTHER
}