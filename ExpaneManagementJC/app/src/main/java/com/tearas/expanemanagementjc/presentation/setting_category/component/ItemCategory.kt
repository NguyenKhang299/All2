package com.tearas.expanemanagementjc.presentation.setting_category.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.tearas.expanemanagementjc.R
import com.tearas.expanemanagementjc.data.dto.CategoryDto
import com.tearas.expanemanagementjc.ui.theme.Blue
import com.tearas.expanemanagementjc.ui.theme.ExpaneManagementJCTheme
import com.tearas.expanemanagementjc.ui.theme.dynamicIconColor
import com.tearas.expanemanagementjc.ui.theme.dynamicTextColor

@Composable
fun ItemCategory(
    categoryDto: CategoryDto = CategoryDto(category = "Category"),
    onUpdate: () -> Unit,
    onStartDestinationEdit: () -> Unit,
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        val (deleteAdd, edit, icon, category, custom) = createRefs()
        IconButton(
            onClick = { onUpdate() },
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .clip(CircleShape)
                .background(if (categoryDto.isDelete) Color.Green else Color.Red)
                .size(20.dp)
                .constrainAs(deleteAdd) {
                    start.linkTo(parent.start)
                    centerVerticallyTo(parent)
                }
                .padding(2.dp)
        ) {
            Icon(
                modifier = Modifier,
                painter = painterResource(
                    id = if (categoryDto.isDelete)
                        R.drawable.baseline_add_24
                    else
                        R.drawable.baseline_horizontal_rule_24
                ),
                contentDescription = null,

                tint = Color.White
            )
        }
        AsyncImage(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Blue)
                .constrainAs(icon) {
                    start.linkTo(deleteAdd.end)
                    centerVerticallyTo(parent)
                }
                .padding(6.dp),
            model = categoryDto.pathIcon,
            contentDescription = null,
            colorFilter = ColorFilter.tint(Color.White)
        )
        Text(
            text = categoryDto.category,
            modifier = Modifier
                .padding(start = 10.dp)
                .constrainAs(category) {
                    start.linkTo(icon.end)
                    end.linkTo(custom.start)
                    width = Dimension.fillToConstraints
                    centerVerticallyTo(parent)
                },
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyLarge,
            color = dynamicTextColor()
        )

        IconButton(
            enabled = !categoryDto.isDefault,
            onClick = { onStartDestinationEdit() },
            modifier = Modifier.constrainAs(edit) {
                end.linkTo(parent.end)
                centerVerticallyTo(parent)
            }) {
            Icon(
                imageVector = if (categoryDto.isDefault) Icons.Filled.Menu else Icons.Filled.Edit,
                contentDescription = null,
                tint = dynamicIconColor()
            )
        }
        Text(
            text = if (!categoryDto.isDefault) "(Custom)" else "",
            modifier = Modifier
                .padding(start = 5.dp)
                .constrainAs(custom) {
                    start.linkTo(category.end)
                    end.linkTo(edit.start)
                    centerVerticallyTo(parent)
                },
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodySmall,
            color = Color.DarkGray
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.2.dp)
                .background(Color.DarkGray)
        )
    }

}

@Preview(showBackground = true)
@Composable
private fun ItemCategoryPre() {
    ExpaneManagementJCTheme {
        ItemCategory(onUpdate = {}, onStartDestinationEdit = {})
    }
}