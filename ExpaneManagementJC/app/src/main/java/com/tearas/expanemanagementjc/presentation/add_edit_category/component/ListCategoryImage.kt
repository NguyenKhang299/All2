package com.tearas.expanemanagementjc.presentation.add_edit_category.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.tearas.expanemanagementjc.domain.model.CategoryImage
import com.tearas.expanemanagementjc.ui.theme.Blue

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ListCategoryImage(
    categoriesImage: Map<String, List<CategoryImage>>,
    itemSelected: CategoryImage? = null,
    onItemSelected: (CategoryImage) -> Unit
) {
    val itemSize = LocalConfiguration.current.screenWidthDp.dp / 5
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        categoriesImage.toMutableMap().forEach { entry ->
            item {
                Text(
                    text = entry.key,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }

            item {
                FlowRow {
                    entry.value.forEach { item ->
                        Box(
                            modifier = Modifier
                                .size(itemSize)
                                .clickable {
                                    onItemSelected(item)
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            AsyncImage(
                                modifier = androidx.compose.ui.Modifier
                                    .size(itemSize / 1.65f)
                                    .clip(CircleShape)
                                    .background(if (item.idCategoryImage == itemSelected?.idCategoryImage) Color.Red else Blue)
                                    .padding(10.dp),
                                model = item.pathIcon,
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(Color.White)
                            )
                        }
                    }
                }
            }
        }
    }
}