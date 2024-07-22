package com.tearas.expanemanagementjc.presentation.setting_category

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.tearas.expanemanagementjc.R
import com.tearas.expanemanagementjc.domain.model.TransactionType
import com.tearas.expanemanagementjc.graph.AddGraph
import com.tearas.expanemanagementjc.presentation.common.DialogWarning
import com.tearas.expanemanagementjc.presentation.common.TabIndicator
import com.tearas.expanemanagementjc.presentation.common.listTab
import com.tearas.expanemanagementjc.presentation.setting_category.component.ItemClickListener
import com.tearas.expanemanagementjc.presentation.setting_category.component.ListCategories
import com.tearas.expanemanagementjc.ui.theme.Blue
import com.tearas.expanemanagementjc.ui.theme.ExpaneManagementJCTheme
import com.tearas.expanemanagementjc.ui.theme.dynamicTextColor
import com.tearas.expanemanagementjc.ui.theme.dynamicTopAppBarColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SettingCategoryScreen(
    viewModel: SettingCateViewModel,
    onBack: () -> Unit,
    onDestination: (String) -> Unit
) {
    val pagerState = rememberPagerState(initialPage = 0)
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var showDialogDelete by remember {
        mutableStateOf(false)
    }
    var isDefault by remember {
        mutableStateOf(false)
    }
    var idCategory by remember {
        mutableLongStateOf(-1)
    }
    Scaffold(topBar = {
        TopAppBar(
            navigationIcon = {
                IconButton(onClick = { onBack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = dynamicTopAppBarColor(),
                navigationIconContentColor = Color.White
            ),
            title = {
                Text(
                    style = MaterialTheme.typography.titleLarge,
                    text = context.getString(R.string.setting_category),
                    color = Color.White
                )
            },
        )
    }, bottomBar = {

        if (showDialogDelete) {
            val dialogTitle = stringResource(R.string.notification_dialog_title)
            val dialogText = stringResource(R.string.notification_dialog_text)
            val txtConfirm = stringResource(R.string.notification_dialog_confirm)
            val txtDismiss = stringResource(R.string.notification_dialog_dismiss)
            DialogWarning(
                onDismissRequest = { showDialogDelete = false },
                onConfirmation = {
                    viewModel.onEvent(SettingEvent.UpdateCategory(idCategory))
                    showDialogDelete = false
                },
                dialogTitle = dialogTitle,
                dialogText = dialogText,
                txtConfirm = txtConfirm,
                txtDisMiss = txtDismiss,
                tint = Color.Red,
                colorConfirm = Color.Red
            )
        }

        Column {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(0.2.dp)
                    .background(Color.DarkGray)
            )
            Button(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .padding(5.dp),
                onClick = { onDestination(AddGraph.AddCategoryScreen.route) },
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Blue)
            ) {
                Text(text = stringResource(id = R.string.add_category), color = Color.White)
            }
        }
    }) { paddingValue ->
        Column(modifier = Modifier.padding(paddingValue)) {
            TabIndicator(listTabs = context.listTab, tabSelected = pagerState.currentPage) {
                scope.launch(Dispatchers.IO) {
                    pagerState.animateScrollToPage(it)
                }
            }
            HorizontalPager(
                modifier = Modifier.fillMaxSize(),
                state = pagerState,
                count = 2,
                key = { it }) {
                val transactionType =
                    if (it == 0) TransactionType.SPEND else TransactionType.COLLECT
                val categories = if (it == 0) {
                    viewModel.onEvent(SettingEvent.GetCategoriesDto(transactionType))
                    viewModel.state.categoriesSpend
                } else {
                    viewModel.onEvent(SettingEvent.GetCategoriesDto(transactionType))
                    viewModel.state.categoriesCollect
                }

                ListCategories(categories, object : ItemClickListener {
                    override fun onStartDestinationEdit(idCategory: Long) {
                        onDestination(AddGraph.EditCategoryScreen.route + "/$idCategory")
                    }

                    override fun onClickUpdate(
                        _idCategory: Long,
                        _isDefault: Boolean,
                        isDelete: Boolean
                    ) {
                        isDefault = _isDefault
                        idCategory = _idCategory
                        if (!isDelete) {
                            viewModel.onEvent(SettingEvent.UpdateCategory(idCategory))
                        } else showDialogDelete = true
                    }
                })
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SettingCategoryScreenPreview() {
    ExpaneManagementJCTheme {
        val viewModel: SettingCateViewModel = hiltViewModel()
        SettingCategoryScreen(viewModel, onBack = {}) {}
    }
}