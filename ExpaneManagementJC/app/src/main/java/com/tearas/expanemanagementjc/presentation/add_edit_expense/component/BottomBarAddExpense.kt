package com.tearas.expanemanagementjc.presentation.add_edit_expense.component

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.tearas.expanemanagementjc.data.dto.CategoryDto
import com.tearas.expanemanagementjc.presentation.add_edit_expense.AddExpenseViewModel
import com.tearas.expanemanagementjc.presentation.add_edit_expense.isValidExpense
import com.tearas.expanemanagementjc.ui.theme.WhiteGray
import com.tearas.expanemanagementjc.ui.theme.isNightMode
import com.tearas.expanemanagementjc.utils.CAMERA_PERMISSIONS
import com.tearas.expanemanagementjc.utils.checkPermission
import com.tearas.expanemanagementjc.utils.format
import com.tearas.expanemanagementjc.utils.isGranted


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BottomBarAddExpense(
    viewModel: AddExpenseViewModel,
    itemCategorySelected: CategoryDto?,
    isUpdate: Boolean
) {
    var showPickImages by remember {
        mutableStateOf(false)
    }

    val permissionLauncher = checkPermission {
        showPickImages = it
    }

    val context = LocalContext.current

    val imePadding = WindowInsets.ime.asPaddingValues().calculateBottomPadding()
    val navigationBarPadding =
        WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

    var height by remember { mutableStateOf(0.dp) }
    var isCheck by remember { mutableStateOf(false) }

    LaunchedEffect(imePadding) {
        if (imePadding > height) {
            height = imePadding - navigationBarPadding - 15.dp
            isCheck = true
        }
    }

    val focusManager = LocalFocusManager.current

    if (showPickImages) BottomPickImages(
        onDismissRequest = { showPickImages = false },
        onImagePicker = {
            viewModel.onPathFileChanged(it)
        }
    )
    AnimatedVisibility(
        modifier = Modifier.background(WhiteGray),
        visible = itemCategorySelected != null,
        enter = slideInVertically() + expandVertically(expandFrom = Alignment.Top) + fadeIn()
    ) {
        Column(
            modifier = Modifier
                .background(if (isNightMode()) MaterialTheme.colorScheme.background else WhiteGray)
                .fillMaxWidth()
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(0.2.dp)
                    .background(if (isNightMode()) Color.White else Color.DarkGray)
            )
            Column(
                modifier = Modifier.padding(15.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    AsyncImage(
                        model = itemCategorySelected?.pathIcon,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(10.dp)
                            .background(Color.Red, CircleShape)
                            .size(50.dp)
                            .padding(10.dp),
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = viewModel.expense,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(end = 15.dp)
                                .clickable {
                                    focusManager.clearFocus()
                                },
                            style = MaterialTheme.typography.titleLarge
                        )
                        TextFieldAddExpense(
                            input = viewModel.note,
                            badge = if (viewModel.pathFile.isNotEmpty()) "1" else "",
                            onValue = { viewModel.onNoteChanged(it) },
                            onCLickEndIcon = {
                                if (isGranted(context, CAMERA_PERMISSIONS)) {
                                    showPickImages = true
                                } else {
                                    permissionLauncher.launch(
                                        arrayOf(CAMERA_PERMISSIONS)
                                    )
                                }
                            }, onCLickBadge = {
                                viewModel.onPathFileChanged("")
                            })
                    }
                }

                CalculatorExpense(
                    viewModel.expense,
                    modifier = Modifier
                        .height(if (WindowInsets.isImeVisible) height else 200.dp)
                        .padding(top = 10.dp)
                ) {
                    viewModel.onExpenseChanged(it)
                }
            }
        }
    }
}