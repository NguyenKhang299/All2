package com.tearas.expanemanagementjc.presentation.setting_category.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.tearas.expanemanagementjc.data.dto.CategoryDto
import com.tearas.expanemanagementjc.ui.theme.ExpaneManagementJCTheme

interface ItemClickListener {
    fun onStartDestinationEdit(idCategory: Long)
    fun onClickUpdate(idCategory: Long, isDefault: Boolean, isDelete: Boolean)
}

@Composable
fun ListCategories(
    list: List<CategoryDto>,
    itemClickListener: ItemClickListener
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        list.forEach {
            item {
                ItemCategory(it,
                    onUpdate = {
                        itemClickListener.onClickUpdate(it.category_id, it.isDefault, !it.isDelete)
                    },
                    onStartDestinationEdit = {
                        itemClickListener.onStartDestinationEdit(it.category_id)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ListCategoriesPre() {
    ExpaneManagementJCTheme {
        ListCategories(listOf(), object : ItemClickListener {
            override fun onStartDestinationEdit(idCategory: Long) {

            }


            override fun onClickUpdate(idCategory: Long, isDefault: Boolean, isDelete: Boolean) {

            }
        })
    }
}