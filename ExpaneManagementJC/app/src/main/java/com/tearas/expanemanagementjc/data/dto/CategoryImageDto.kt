package com.tearas.expanemanagementjc.data.dto

import androidx.room.ColumnInfo
import com.tearas.expanemanagementjc.domain.model.CategoryType

class CategoryImageSourceDto(
    val type: CategoryType,
    val paths: List<String>
) {
}