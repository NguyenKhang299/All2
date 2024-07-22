package com.tearas.expanemanagementjc.presentation.add_edit_expense.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tearas.expanemanagementjc.data.dto.CategoryDto
import com.tearas.expanemanagementjc.domain.model.TransactionType
import com.tearas.expanemanagementjc.ui.theme.ExpaneManagementJCTheme

@Composable
fun ListCategories(
    listCategory: List<CategoryDto>,
    categoryClick: CategoryDto?,
    itemCategoryClick: (CategoryDto) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        horizontalArrangement = Arrangement.Center
    ) {
        items(listCategory) { it ->
            ItemCategory(categoryClick, category = it) {
                itemCategoryClick(it)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ListCategoriesPreview() {
    ExpaneManagementJCTheme {
        ListCategories(
            listCategory = listOf(
                CategoryDto(2, "Category", "",TransactionType.SPEND),
                CategoryDto(1, "Category", "",TransactionType.SPEND),
                CategoryDto(1, "Category", "",TransactionType.SPEND),
                CategoryDto(1, "Category", "",TransactionType.SPEND),
                CategoryDto(1, "Category", "",TransactionType.SPEND),
                CategoryDto(1, "Category", "",TransactionType.SPEND),
                CategoryDto(1, "Category", "",TransactionType.SPEND),
                CategoryDto(1, "Category", "",TransactionType.SPEND),
                CategoryDto(1, "Category", "",TransactionType.SPEND),
                CategoryDto(1, "Category", "",TransactionType.SPEND)
            ),
            CategoryDto(2, "Category", "",TransactionType.SPEND)
        ) {}
    }
}