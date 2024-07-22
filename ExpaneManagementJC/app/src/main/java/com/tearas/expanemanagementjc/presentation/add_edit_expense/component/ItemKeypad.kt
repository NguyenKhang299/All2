package com.tearas.expanemanagementjc.presentation.add_edit_expense.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tearas.expanemanagementjc.R
import com.tearas.expanemanagementjc.ui.theme.Blue
import com.tearas.expanemanagementjc.ui.theme.ExpaneManagementJCTheme

@Composable
fun ItemKeypad(modifier: Modifier = Modifier, key: Key, onKeyClick: (Key) -> Unit) {
    Card(
        shape = RoundedCornerShape(5.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 0.dp),
        modifier = modifier.padding(vertical = 5.dp, horizontal = 10.dp)
    ) {
        Column(
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth()
                .background(
                    if (key.isSign || key.isDelete) Blue else Color.White
                )
                .clickable { onKeyClick(key) }
                .padding(5.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (key.isDelete) {
                Icon(
                    painter = painterResource(id = R.drawable.delete),
                    contentDescription = null,
                    tint = Color.White
                )
            } else {
                Text(
                    text = key.key,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    color = if (key.isSign) Color.White else Blue
                )
            }
        }
    }
}

@Preview
@Composable
private fun ItemKeypadPreview() {
    ExpaneManagementJCTheme {
        ItemKeypad(key = Key("c")) {

        }
    }
}