package com.tearas.expanemanagementjc.presentation.details

import android.annotation.SuppressLint
import androidx.collection.mutableIntSetOf
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.tearas.expanemanagementjc.R
import com.tearas.expanemanagementjc.data.dto.ExpenseDto
import com.tearas.expanemanagementjc.presentation.common.DialogDatePicker
import com.tearas.expanemanagementjc.presentation.common.DialogWarning
import com.tearas.expanemanagementjc.presentation.details.component.BottomBarDetails
import com.tearas.expanemanagementjc.presentation.details.component.DetailTopAppBar
import com.tearas.expanemanagementjc.presentation.details.component.DetailsButton
import com.tearas.expanemanagementjc.presentation.details.component.DialogImage
import com.tearas.expanemanagementjc.ui.theme.Blue
import com.tearas.expanemanagementjc.ui.theme.dynamicBodyColor
import com.tearas.expanemanagementjc.ui.theme.dynamicCardColor
import com.tearas.expanemanagementjc.ui.theme.dynamicLabelColor
import com.tearas.expanemanagementjc.ui.theme.dynamicLineColor
import com.tearas.expanemanagementjc.utils.FileHelper
import com.tearas.expanemanagementjc.utils.format
import com.tearas.expanemanagementjc.utils.formatDateTime

@OptIn(ExperimentalSharedTransitionApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailsScreen(
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    viewModel: DetailsViewModel,
    idExpense: Long,
    onBack: () -> Unit,
    onClickEdit: (Long) -> Unit
) {
    val context = LocalContext.current
    var isShowDialog by rememberSaveable {
        mutableStateOf(false)
    }
    var isShowDialogImage by rememberSaveable {
        mutableStateOf(false)
    }
    val detailsState = viewModel.detailsState
    val expenseDto = detailsState.expenseDto
    LaunchedEffect(Unit, detailsState.isDeleted) {
        viewModel.onEvent(DetailsEvent.GetDetails(idExpense))
        if (detailsState.isDeleted) {
            isShowDialog = false
            onBack()
        }
    }
    if (isShowDialogImage && expenseDto != null) {
        DialogImage(uri = FileHelper.getUri(context, expenseDto.pathFile)) {
            isShowDialogImage = false
        }
    }
    if (expenseDto != null) {
        val dialogTitle = stringResource(R.string.delete_warning_dialog_title)
        val dialogText = stringResource(R.string.delete_warning_dialog_text)
        val txtConfirm = stringResource(R.string.delete_warning_dialog_confirm)
        if (isShowDialog) {
            DialogWarning(
                onDismissRequest = { isShowDialog = false },
                onConfirmation = {
                    viewModel.onEvent(DetailsEvent.Delete(id = idExpense))
                },
                dialogTitle = dialogTitle,
                dialogText = dialogText,
                txtConfirm = txtConfirm,
                tint = Color.Red,
                colorConfirm = Color.Red,
                colorDismiss = Blue,
            )
        }

        with(sharedTransitionScope) {
            Scaffold(modifier = Modifier.fillMaxSize(),
                topBar = {
                    DetailTopAppBar {
                        onBack()
                    }
                }, bottomBar = {
                    BottomBarDetails(
                        onClickEdit = { onClickEdit(expenseDto.expenseId) },
                        onClickDelete = { isShowDialog = true }
                    )
                }
            ) { paddingValues ->
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    ElevatedCard(
                        modifier = Modifier.padding(20.dp),
                        colors = CardDefaults.cardColors(containerColor = dynamicCardColor())
                    ) {
                        Column(
                            modifier = Modifier
                                .verticalScroll(rememberScrollState())
                                .padding(15.dp),
                            verticalArrangement = Arrangement.Top
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 20.dp, top = 20.dp)
                            ) {
                                AsyncImage(
                                    model = expenseDto.pathIcon,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .sharedElement(
                                            rememberSharedContentState(key = "image-${expenseDto.expenseId}"),
                                            animatedVisibilityScope = animatedContentScope
                                        )
                                        .background(Blue, CircleShape)
                                        .size(60.dp)
                                        .padding(12.dp),
                                    colorFilter = ColorFilter.tint(Color.White)
                                )
                                Text(
                                    modifier = Modifier
                                        .sharedElement(
                                            rememberSharedContentState(key = "text-${expenseDto.expenseId}id"),
                                            animatedVisibilityScope = animatedContentScope
                                        )
                                        .padding(start = 15.dp),
                                    text = "${expenseDto.category}  ",
                                    style = MaterialTheme.typography.titleLarge,
                                    color = dynamicBodyColor()
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 30.dp)
                            ) {
                                Column {

                                    DetailText(
                                        label = context.getString(R.string.type),
                                        value = expenseDto.transactionType.name
                                    )
                                    DetailText(
                                        label = context.getString(R.string.amount_of_money),
                                        value = expenseDto.expense.format()
                                    )
                                    DetailText(
                                        label = context.getString(R.string.day),
                                        value = expenseDto.date.formatDateTime()
                                    )
                                    DetailText(
                                        label = context.getString(R.string.note),
                                        value = expenseDto.note
                                    )
                                    AsyncImage(
                                        contentDescription = null,
                                        model = FileHelper.getUri(context, expenseDto.pathFile),
                                        modifier = Modifier
                                            .padding(start = 15.dp)
                                            .height(80.dp)
                                            .clickable { isShowDialogImage = true }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DetailText(label: String, value: String) {
    Row {
        Text(
            modifier = Modifier.padding(start = 15.dp),
            text = "$label:",
            style = MaterialTheme.typography.labelLarge,
            color = dynamicLabelColor(),
            minLines = 2
        )
        Text(
            modifier = Modifier.padding(start = 15.dp),
            text = value,
            style = MaterialTheme.typography.labelLarge,
            minLines = 2,
            color = dynamicBodyColor()
        )
    }
}

