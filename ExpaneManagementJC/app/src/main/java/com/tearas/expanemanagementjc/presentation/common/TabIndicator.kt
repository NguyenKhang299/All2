package com.tearas.expanemanagementjc.presentation.common

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.tearas.expanemanagementjc.R
import com.tearas.expanemanagementjc.presentation.add_edit_expense.component.TabItem
import com.tearas.expanemanagementjc.ui.theme.Blue
import com.tearas.expanemanagementjc.ui.theme.LightBlue

val Context.listTab: List<TabItem>
    get() = listOf(
        TabItem(getString(R.string.expense), 0),
        TabItem(getString(R.string.income), 1)
    )

@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun TabIndicator(
    modifier: Modifier = Modifier,
    listTabs: List<TabItem>,
    tabSelected: Int,
    onTabSelected: (Int) -> Unit
) {
    BoxWithConstraints(
        modifier = modifier.padding(15.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(LightBlue)
            .height(50.dp)
            .padding(5.dp)

    ) {
        val maxWidth = this.maxWidth
        val tabWidth = maxWidth / listTabs.size

        val indicatorOffset by animateDpAsState(
            targetValue = tabWidth * tabSelected,
            animationSpec = tween(durationMillis = 200, easing = FastOutLinearInEasing),
            label = "indicator offset"
        )

        Box(
            modifier = Modifier
                .offset(x = indicatorOffset)
                .clip(RoundedCornerShape(5.dp))
                .background(Blue)
                .fillMaxWidth(1f / listTabs.size)
                .fillMaxHeight()
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()

        ) {
            listTabs.forEach {
                TabItem(
                    text = it.name,
                    selected = tabSelected == it.position,
                    onClick = { onTabSelected(it.position) },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                )
            }
        }
    }
}