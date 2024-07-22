package com.tearas.expanemanagementjc.presentation.remind.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tearas.expanemanagementjc.domain.model.RemindModel
import com.tearas.expanemanagementjc.presentation.home.component.CardItem
import com.tearas.expanemanagementjc.utils.format
import com.tearas.expanemanagementjc.utils.formatDateTime
import com.tearas.expanemanagementjc.utils.formatTime
import java.util.Date

@Composable
fun ItemRemind(modifier: Modifier = Modifier, remindModel: RemindModel, onClick: () -> Unit) {
    CardItem(modifier.clickable { onClick() }) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = remindModel.title,
                    modifier = Modifier.weight(8f),
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium
                )
                Row(
                 ) {
                    Text(
                        text = Date(remindModel.time).formatTime(),
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null
                    )
                }
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(0.2.dp)
                    .background(Color.DarkGray)
            )
        }
    }
}

@Preview
@Composable
private fun ItemRemindPreView() {
    ItemRemind(remindModel = RemindModel()) {}
}