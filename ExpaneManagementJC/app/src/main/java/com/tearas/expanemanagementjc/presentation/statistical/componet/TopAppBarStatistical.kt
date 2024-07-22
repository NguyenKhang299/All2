package com.tearas.expanemanagementjc.presentation.statistical.componet

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tearas.expanemanagementjc.R
import com.tearas.expanemanagementjc.domain.model.TransactionType
import com.tearas.expanemanagementjc.ui.theme.Blue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarStatistical(
    onClickChangeType: (TransactionType) -> Unit,
    onClickDateDialog: () -> Unit
) {
    var clickChangeType by remember { mutableStateOf(false) }

    val animateRotation by animateFloatAsState(
        targetValue = if (clickChangeType) 90f else 0f,
        animationSpec = tween(
            durationMillis = 1000,
            easing = LinearOutSlowInEasing
        )
    )
    TopAppBar(
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = if (!clickChangeType) stringResource(id = R.string.expense) else stringResource(
                            id = R.string.income
                        ),
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(
                        onClick = {
                            clickChangeType = !clickChangeType
                            val type =
                                if (clickChangeType) TransactionType.COLLECT else TransactionType.SPEND
                            onClickChangeType(type)
                        },
                     ) {
                        Icon(
                            modifier = Modifier
                                .graphicsLayer {
                                    rotationZ = animateRotation
                                }
                                .size(24.dp),
                            painter = painterResource(id = R.drawable.baseline_autorenew_24),
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Blue
        ),
        actions = {
            IconButton(onClick = { onClickDateDialog() }) {
                Icon(
                    imageVector = Icons.Filled.DateRange,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    )
}