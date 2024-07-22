package com.tearas.expanemanagementjc.presentation.common

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.FabPosition
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.tearas.expanemanagementjc.R
import com.tearas.expanemanagementjc.ui.theme.Blue
import com.tearas.expanemanagementjc.ui.theme.ExpaneManagementJCTheme
import com.tearas.expanemanagementjc.ui.theme.WhiteGray
import com.tearas.expanemanagementjc.ui.theme.WhiteGray2
import com.tearas.expanemanagementjc.ui.theme.dynamicIconColor
import com.tearas.expanemanagementjc.ui.theme.dynamicTextColor
import com.tearas.expanemanagementjc.ui.theme.isNightMode
import com.tearas.expanemanagementjc.ui.theme.md_theme_dark_background_appbar
import kotlinx.coroutines.launch
import java.util.Calendar


val listMonths = (1..12).toList()
val listYears = (1975..2075).toList()

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun DatePickerDialogCustom(
    month: Int, year: Int, onDismissRequest: () -> Unit, onConfirmed: (Int, Int) -> Unit
) {

    var monthSelected by remember {
        mutableIntStateOf(month)
    }

    var yearSelected by remember {
        mutableIntStateOf(year)
    }

    var pageSelected by remember {
        mutableIntStateOf(listYears.indexOf(yearSelected))
    }
    var showYearPicker by remember {
        mutableStateOf(false)
    }

    val pagerState = rememberPagerState(listYears.indexOf(yearSelected))
    val scopes = rememberCoroutineScope()
    yearSelected = listYears[pagerState.currentPage]


    DatePickerDialog(colors = DatePickerDefaults.colors(
        containerColor = if (isNightMode()) md_theme_dark_background_appbar else WhiteGray
    ),
        modifier = Modifier,
        properties = DialogProperties(
            dismissOnClickOutside = true
        ),
        onDismissRequest = { onDismissRequest() },
        dismissButton = {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                onClick = { onDismissRequest() },
            ) { Text(text = "Cancel", color = Color.Gray) }
        },
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                onClick = { onConfirmed(monthSelected, listYears[pageSelected]) },
            ) { Text(text = "OK", color = Blue) }
        }) {
        Column(modifier = Modifier.padding(top = 15.dp)) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.selected_date),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 18.sp),
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.SpaceBetween
            ) {
                Column(modifier = Modifier) {
                    Button(
                        onClick = {
                            showYearPicker = !showYearPicker
                        }, colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        )
                    ) {
                        Text(text = yearSelected.toString(), color = dynamicTextColor())
                        Icon(
                            imageVector = Icons.Filled.ArrowDropDown,
                            contentDescription = null,
                            tint = dynamicIconColor()
                        )
                    }

                    AnimatedVisibility(
                        visible = showYearPicker,
                        enter = expandVertically() + fadeIn(),
                    ) {
                        DropdownMenu(modifier = Modifier
                            .animateContentSize()
                            .wrapContentWidth()
                            .height(150.dp),
                            expanded = true,
                            onDismissRequest = { showYearPicker = false }) {
                            listYears.forEach {
                                DropdownMenuItem(modifier = Modifier.width(80.dp), text = {
                                    Text(text = "$it")
                                }, onClick = {
                                    scopes.launch {
                                        showYearPicker = false
                                        yearSelected = it
                                        pagerState.scrollToPage(listYears.indexOf(yearSelected))
                                    }
                                })
                            }
                        }
                    }
                }
                Row {
                    fun navigateToPage(offset: Int) {
                        scopes.launch {
                            val newIndex = pagerState.currentPage + offset
                            if (newIndex in listYears.indices) {
                                pagerState.animateScrollToPage(newIndex)
                                yearSelected = listYears[newIndex]
                            }
                        }
                    }

                    IconButton(onClick = { navigateToPage(-1) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowLeft,
                            contentDescription = null
                        )
                    }
                    IconButton(onClick = { navigateToPage(1) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                            contentDescription = null
                        )
                    }
                }
            }
            HorizontalPager(
                state = pagerState, count = listYears.size
            ) { pagePosition ->
                BoxListItemYearPicker(
                    monthSelected = monthSelected,
                    pageSelected = pageSelected,
                    pagePosition = pagePosition
                ) {
                    monthSelected = it
                    pageSelected = pagePosition
                }
            }
        }
    }
}

@Composable
fun BoxListItemYearPicker(
    modifier: Modifier = Modifier,
    monthSelected: Int,
    pageSelected: Int,
    pagePosition: Int,
    onMonthClick: (Int) -> Unit
) {
    val context = LocalContext.current
    LazyVerticalGrid(modifier = modifier.padding(horizontal = 5.dp), columns = GridCells.Fixed(3)) {
        itemsIndexed(listMonths) { i, month ->
            ItemYearPicker(
                modifier = Modifier.padding(horizontal = 5.dp),
                month = context.getString(R.string.mth) + " " + month,
                isSelected = monthSelected == month && pagePosition == pageSelected
            ) {
                onMonthClick(month)
            }
        }
    }
}

@Composable
fun ItemYearPicker(
    modifier: Modifier = Modifier,
    month: String,
    isSelected: Boolean = false,
    onClick: (String) -> Unit
) {
    Button(
        modifier = modifier, onClick = { onClick(month) }, colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Blue else if (isNightMode()) MaterialTheme.colorScheme.background else Color.White
        )
    ) {
        Text(
            maxLines = 1,
            text = month,
            color = if (isSelected) Color.White else if (isNightMode()) Color.LightGray else Color.Black
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DatePickerDialogCustomPreview() {
    ExpaneManagementJCTheme {
        Column {
            DatePickerDialogCustom(4, 2024, {}, { month, year -> })
//            ItemYearPicker(month = "Thg 12", isSelected = true) {}
//            ItemYearPicker(month = "Thg 12", isSelected = false) {}
//            BoxListItemYearPicker()
        }
    }
}