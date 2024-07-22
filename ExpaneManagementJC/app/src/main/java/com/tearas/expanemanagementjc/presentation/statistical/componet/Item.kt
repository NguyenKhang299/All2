package com.tearas.expanemanagementjc.presentation.statistical.componet

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.tearas.expanemanagementjc.data.dto.StatisticalDto
import com.tearas.expanemanagementjc.ui.theme.Blue
import com.tearas.expanemanagementjc.ui.theme.ExpaneManagementJCTheme
import com.tearas.expanemanagementjc.ui.theme.LightBlue
import com.tearas.expanemanagementjc.utils.format
import com.tearas.expanemanagementjc.utils.formatDateTime

@Composable
fun Item(
    maxValue: Int,
    entry: StatisticalDto,
    isShowDate: Boolean = false,
    onClickItem: (StatisticalDto) -> Unit
) {
    var boxWidth by rememberSaveable { mutableStateOf(0f) }
    val density = LocalDensity.current
    val colorsChart = listOf(
        Blue,
        Color(0xFFFFC400),
        Color(0xFFCD44E4),
        Color(0xFF1DE9B6),
        Color(0xFFBBE621),
        LightBlue
    )

    val randomColor = remember { colorsChart.random() }
    val widthSlider = ((entry.percentage / 100) * boxWidth).toInt()
    Column(modifier = Modifier
        .fillMaxWidth()
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = rememberRipple(color = Color.Blue)//chỉ báo
        ) { onClickItem(entry) }) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 10.dp)
        ) {
            val (percent, icon, categoryName, expense, slider, parameter, date) = createRefs()

            AsyncImage(
                modifier = Modifier
                    .constrainAs(icon) {
                        start.linkTo(parent.start)
                        centerVerticallyTo(parent)
                    }
                    .size(50.dp)
                    .background(
                        shape = CircleShape,
                        color = randomColor
                    )
                    .padding(10.dp),
                model = entry.pathIcon,
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.White)
            )

            ConstraintLayout(
                modifier = Modifier
                    .padding(start = 15.dp)
                    .constrainAs(parameter) {
                        start.linkTo(icon.end)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                        centerVerticallyTo(parent)
                    }
            ) {
                Text(
                    modifier = Modifier.constrainAs(categoryName) {
                        start.linkTo(parent.start)
                    },
                    text = entry.category
                )

                Text(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .constrainAs(percent) {
                            start.linkTo(categoryName.end)
                        },
                    text = "${entry.percentage.format()}%"
                )

                if (isShowDate) {
                    Text(
                        modifier = Modifier
                            .padding(top = 5.dp)
                            .constrainAs(date) {
                                start.linkTo(icon.end)
                                top.linkTo(slider.bottom)
                            },
                        text = entry.date.formatDateTime()
                    )
                }

                Text(
                    modifier = Modifier
                        .padding(start = 15.dp)
                        .constrainAs(expense) {
                            start.linkTo(percent.end)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        },
                    text = entry.totalExpenses.format(),
                    textAlign = TextAlign.End,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Box(
                    modifier = Modifier
                        .onGloballyPositioned {
                            boxWidth = it.size.width.toFloat()
                        }
                        .padding(top = 5.dp)
                        .constrainAs(slider) {
                            top.linkTo(categoryName.bottom)
                            width = Dimension.fillToConstraints // Ensure it fills to constraints
                        }
                        .fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .width(with(density) { widthSlider.toDp() })
                            .height(6.dp)
                            .background(
                                shape = RoundedCornerShape(5.dp),
                                color = Blue
                            )
                    )
                }
            }
        }


        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.2.dp)
                .background(Color.LightGray)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ItemPreview() {
    ExpaneManagementJCTheme {

    }
}