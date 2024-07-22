package com.tearas.expanemanagementjc.presentation.details.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tearas.expanemanagementjc.ui.theme.Blue
import com.tearas.expanemanagementjc.ui.theme.dynamicLineColor

@Composable
fun BottomBarDetails(
    onClickEdit: () -> Unit,
    onClickDelete: () -> Unit
) {
    Column(
        modifier = Modifier
            .animateContentSize()
            .background(Blue)
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = Blue),
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
            shape = RoundedCornerShape(0)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                DetailsButton(modifier = Modifier.fillMaxWidth(0.5f),
                    text = "Edit",
                    onClick = {
                        onClickEdit()
                    })
                Spacer(
                    modifier = Modifier
                        .width(0.5.dp)
                        .height(20.dp)
                        .background(dynamicLineColor())
                )
                DetailsButton(modifier = Modifier.fillMaxWidth(1f),
                    text = "Delete",
                    onClick = {
                        onClickDelete()
                    })
            }
        }
    }
}