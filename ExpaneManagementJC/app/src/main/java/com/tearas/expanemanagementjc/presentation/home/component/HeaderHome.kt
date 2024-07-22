package com.tearas.expanemanagementjc.presentation.home.component

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tearas.expanemanagementjc.R
import com.tearas.expanemanagementjc.presentation.common.AutoResizedText
import com.tearas.expanemanagementjc.presentation.common.DatePickerDialogCustom
import com.tearas.expanemanagementjc.ui.theme.Blue
import com.tearas.expanemanagementjc.ui.theme.ExpaneManagementJCTheme
import com.tearas.expanemanagementjc.ui.theme.isNightMode
import com.tearas.expanemanagementjc.utils.format
import java.util.Calendar


@Composable
fun HeaderHome(
    expense: Double,
    income: Double,
    surplus: Double,
    onSearchClick: () -> Unit,
    onDateClick: (Int, Int) -> Unit
) {
//    var _expense by remember { mutableFloatStateOf(0f) }
//    var _income by remember { mutableFloatStateOf(0f) }
//    var _surplus by remember { mutableFloatStateOf(0f) }
//
//    LaunchedEffect(Unit) {
//        _expense = expense
//        _income = income
//        _surplus = surplus
//    }
//
//    val expenseCounted: Float by animateFloatAsState(
//        targetValue = _expense,
//        animationSpec = tween(
//            durationMillis = 500,
//            easing = FastOutLinearInEasing
//        ), label = ""
//    )

//    val incomeCounted: Float by animateFloatAsState(
//        targetValue = _income,
//        animationSpec = tween(
//            durationMillis = 500,
//            easing = FastOutLinearInEasing
//        ), label = ""
//    )
//
//    val surplusCounted: Float by animateFloatAsState(
//        targetValue = _surplus,
//        animationSpec = tween(
//            durationMillis = 500,
//            easing = FastOutLinearInEasing
//        ), label = ""
//    )
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = System.currentTimeMillis()

    var showDateDialog by remember {
        mutableStateOf(false)
    }
    var month by remember {
        mutableIntStateOf(calendar[Calendar.MONTH] + 1)
    }

    var year by remember {
        mutableIntStateOf(calendar[Calendar.YEAR])
    }
    val context = LocalContext.current
    if (showDateDialog) {
        DatePickerDialogCustom(
            month,
            year,
            onDismissRequest = { showDateDialog = false },
            onConfirmed = { m, y ->
                showDateDialog = false
                month = m
                year = y
                onDateClick(m, y)
            },
        )
    }
    Column(
        modifier = Modifier
            .background(if (isNightMode()) MaterialTheme.colorScheme.background else Blue)
            .padding(vertical = 10.dp)
            .statusBarsPadding()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { }, enabled = false) {
                Icon(
                    imageVector = Icons.Filled.DateRange,
                    contentDescription = null,
                    tint = Color.White
                )
            }
            AutoResizedText(
                text = context.getString(R.string.app_name),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                ),
                color = Color.White
            )
            IconButton(onClick = { onSearchClick() }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }

        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Column(
                modifier = Modifier,
            ) {

                Text(
                    textAlign = TextAlign.Start,
                    text = "$year",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Normal),
                    color = Color.LightGray
                )
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        textAlign = TextAlign.Start,
                        text = "${context.getString(R.string.month)} " + month,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                        maxLines = 1,
                        color = Color.White
                    )
                    IconButton(
                        onClick = { showDateDialog = !showDateDialog },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            tint = Color.White,
                            imageVector = Icons.Filled.ArrowDropDown,
                            contentDescription = null,
                        )
                    }
                }
                Text(
                    modifier = Modifier
                        .padding(top = 10.dp, end = 10.dp),
                    text = "$ " + surplus.format(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White,
                    maxLines = 1,
                    fontSize = 22.sp,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Column(
                modifier = Modifier.padding(end = 3.dp)
            ) {
                BoxExpense(
                    modifier = Modifier,
                    icon = R.drawable.income,
                    title = context.getString(R.string.income),
                    body = income.format(),
                    isDecrease = false
                )

                BoxExpense(
                    modifier = Modifier
                        .padding(top = 10.dp),
                    icon = R.drawable.expense,
                    title = context.getString(R.string.expense),
                    body = expense.format(),
                    isDecrease = true
                )
            }
        }
    }
}

@Composable
fun BoxExpense(
    modifier: Modifier = Modifier,
    icon: Int,
    title: String,
    body: String,
    isDecrease: Boolean
) {
    Row(
        modifier = modifier
            .widthIn(130.dp)
            .padding(5.dp)
    ) {

        Column(modifier = Modifier.padding(start = 10.dp)) {
            Text(
                textAlign = TextAlign.Start,
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Normal),
                maxLines = 1,
                color = Color.White
            )
            Text(
                textAlign = TextAlign.Start,
                text = body,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                color = Color.White
            )
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HeaderHomePreview() {
    ExpaneManagementJCTheme {
        HeaderHome(1000.0, 2000.0, 3000.0, onSearchClick = { }) { m, y ->

        }
    }
}