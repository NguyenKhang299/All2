package com.tearas.expanemanagementjc.presentation.home.component

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.tearas.expanemanagementjc.data.dto.ExpenseDto
import com.tearas.expanemanagementjc.domain.model.ProfitLoss
import com.tearas.expanemanagementjc.ui.theme.Blue
import com.tearas.expanemanagementjc.ui.theme.HeaderColor
import com.tearas.expanemanagementjc.utils.format
import com.tearas.expanemanagementjc.R
import com.tearas.expanemanagementjc.domain.model.TransactionType
import com.tearas.expanemanagementjc.ui.theme.dynamicTextColor
import com.tearas.expanemanagementjc.ui.theme.isNightMode
import com.tearas.expanemanagementjc.ui.theme.md_theme_dark_background_appbar
import com.tearas.expanemanagementjc.utils.formatDateMonth

@Composable
fun ItemExpenseHeader(profitLoss: ProfitLoss, onClickHeader: () -> Unit) {
    CardItem {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { onClickHeader() }
                .background(if (!isNightMode()) HeaderColor else md_theme_dark_background_appbar), Arrangement.SpaceBetween
        ) {
            Text(
                text = profitLoss.date.formatDateMonth(),
                modifier = Modifier.padding(5.dp),
                color = dynamicTextColor()
            )
            Text(
                text = (profitLoss.collect - profitLoss.spend).format(),
                modifier = Modifier.padding(5.dp),
                color = dynamicTextColor()
            )
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ItemExpense(
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedContentScope: AnimatedContentScope? = null,
    expense: ExpenseDto,
    onClickItem: (Int) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    with(sharedTransitionScope) {
        Row(
            modifier = Modifier
                .animateContentSize()
                .fillMaxWidth()
                .clickable(
                    interactionSource = interactionSource,
                    indication = rememberRipple(color = Color.Blue)//chỉ báo
                ) {
                    onClickItem(expense.expenseId.toInt())
                }
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = if (this@with != null && animatedContentScope != null) Modifier
                        .sharedElement(
                            rememberSharedContentState(key = "image-${expense.expenseId.toInt()}"),
                            animatedVisibilityScope = animatedContentScope
                        )
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(Blue) else Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(Blue),
                ) {
                    AsyncImage(
                        model = expense.pathIcon,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(10.dp)
                            .size(50.dp),
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                }
                Column {
                    Text(
                        text = expense.category,
                        style = MaterialTheme.typography.titleMedium.copy(fontFamily = FontFamily.Serif),
                        modifier = if (this@with != null && animatedContentScope != null) Modifier
                            .padding(start = 15.dp)
                            .sharedElement(
                                rememberSharedContentState(key = "text-${expense.expenseId.toInt()}"),
                                animatedVisibilityScope = animatedContentScope,
                            ) else Modifier
                            .padding(start = 15.dp),
                        color = dynamicTextColor()
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = expense.note,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 15.dp), color = dynamicTextColor()
                        )
                        if (expense.pathFile.isNotEmpty()) Icon(
                            painter = painterResource(id = R.drawable.attach_file),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(start = 5.dp)
                                .size(20.dp)
                        )
                    }
                }
            }
            val transactionSign = if (expense.transactionType == TransactionType.SPEND) "-" else "+"
            Text(
                text = transactionSign + expense.expense.format(),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(start = 15.dp), color = dynamicTextColor()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewItemExpense() {
    val expense = ExpenseDto(
        expenseId = 1,
        category = "Food",
        note = "Dinner at restaurant",
        expense = 45.99,
        pathIcon = "https://via.placeholder.com/150"
    )


}

