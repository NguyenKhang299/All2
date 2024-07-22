package com.tearas.expanemanagementjc.presentation.statistical.componet

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tearas.expanemanagementjc.data.dto.StatisticalDto

@Composable
fun ListItemStatistical(
    modifier: Modifier = Modifier,
    maxValue: Double,
    data: List<StatisticalDto>,
    isShowDate: Boolean = false,
    onClickItem: (StatisticalDto) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
    ) {
        data.forEachIndexed { index, value ->
            item {
                Item(maxValue.toInt(), value, isShowDate, onClickItem = {
                    onClickItem(it)
                })
            }
        }
    }
}