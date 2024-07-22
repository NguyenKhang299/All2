package com.tearas.expanemanagementjc.presentation.add_edit_category

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.tearas.expanemanagementjc.R
import com.tearas.expanemanagementjc.domain.model.CategoryImage
import com.tearas.expanemanagementjc.domain.model.TransactionType
import com.tearas.expanemanagementjc.presentation.add_edit_category.component.Header
import com.tearas.expanemanagementjc.presentation.add_edit_category.component.ListCategoryImage
import com.tearas.expanemanagementjc.presentation.add_edit_category.component.TopAppBarAddCate
import com.tearas.expanemanagementjc.presentation.common.TabIndicator
import com.tearas.expanemanagementjc.presentation.common.listTab
import com.tearas.expanemanagementjc.ui.theme.ExpaneManagementJCTheme

@Composable
fun AddEditCategoryScreen(
    isAddCategory: Boolean = false,
    viewModel: AddEditCategoryViewModel,
    onBack: () -> Unit
) {

    val context = LocalContext.current
    var tabSelected by remember {
        mutableIntStateOf(0)
    }
    var itemSelected by remember {
        mutableStateOf<CategoryImage?>(null)
    }
    if (viewModel.state.isSuccess == true) {
        onBack()
    }

    Scaffold(topBar = {
        TopAppBarAddCate(
            if (isAddCategory) context.getString(R.string.add_category)
            else context.getString(R.string.edit_category),
            viewModel.isCategoryValid,
            onClickBack = { onBack() },
            onClickDone = {
                if (isAddCategory)
                    viewModel.onEvent(AddEditCategoryEvent.AddCategory)
                else
                    viewModel.onEvent(AddEditCategoryEvent.EditCategory)
            })
    }) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            TabIndicator(listTabs = context.listTab, tabSelected = tabSelected) {
                tabSelected = it
                val transaction = if (it == 0) TransactionType.SPEND else TransactionType.COLLECT
                viewModel.updateTransactionType(transaction)
            }

            Header(
                pathIcon = viewModel.categoryImgSelected?.pathIcon,
                viewModel.categoryName
            ) {
                viewModel.updateCategoryName(it)
            }

            ListCategoryImage(viewModel.state.categoriesImage, viewModel.categoryImgSelected) {
                viewModel.updateCategoryImage(it)
            }
        }
    }
}


@Preview
@Composable
private fun AddCategoryScreenPreview() {
    ExpaneManagementJCTheme {
        val viewmodel: AddEditCategoryViewModel = hiltViewModel()
        AddEditCategoryScreen(false, viewmodel) {}
    }
}