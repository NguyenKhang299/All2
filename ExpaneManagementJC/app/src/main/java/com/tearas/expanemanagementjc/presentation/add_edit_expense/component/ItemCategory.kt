package com.tearas.expanemanagementjc.presentation.add_edit_expense.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.tearas.expanemanagementjc.data.dto.CategoryDto
import com.tearas.expanemanagementjc.domain.model.TransactionType
import com.tearas.expanemanagementjc.ui.theme.Blue


@Composable
fun ItemCategory(
    categorySelected: CategoryDto?,
    category: CategoryDto,
    onItemClick: (CategoryDto) -> Unit
) {
    val isSelected = category.category_id == categorySelected?.category_id
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .aspectRatio(1f)
            .clickable { onItemClick(category) }
    ) {
        AsyncImage(
            model = category.pathIcon,
            contentDescription = "",
            modifier = Modifier.padding(5.dp)
                .weight(6f)
                .aspectRatio(1f)
                .background(
                    if (isSelected) Color.Red else Blue,
                    CircleShape
                )
                .padding(10.dp),
            colorFilter = ColorFilter.tint(Color.White)
        )
        Text(
            modifier = Modifier
                .weight(4f)
                .fillMaxWidth(),
            text = category.category,
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Center,
            maxLines = 2,
        )
    }
}

@Preview
@Composable
private fun ItemCategoryPreview() {
    ItemCategory(
        CategoryDto(
            1,
            "Category",
            "file:///assets/img_categories/festival/birth-day.png",
            TransactionType.SPEND
        ),
        CategoryDto(
            1,
            "Category",
            "file:///assets/img_categories/festival/birth-day.png",
            TransactionType.SPEND
        )
    ) {}
}