package com.tearas.expanemanagementjc.presentation.add_edit_category.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.tearas.expanemanagementjc.ui.theme.Blue

@Composable
fun Header(pathIcon: String?,text:String, onTextChanged: (String) -> Unit) {
    val itemSize = LocalConfiguration.current.screenWidthDp.dp / 5

    Row(
        modifier = Modifier
            .wrapContentHeight()
            .padding(15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .padding(end = 15.dp)
                .size(itemSize / 1.65f)
                .clip(CircleShape)
                .background(Blue)
                .padding(10.dp),
            model = pathIcon,
            contentDescription = null,
            colorFilter = ColorFilter.tint(Color.White)
        )
        TextFieldAddCategory(modifier = Modifier.weight(8.5f),text) {
            onTextChanged(it)
        }
    }
}