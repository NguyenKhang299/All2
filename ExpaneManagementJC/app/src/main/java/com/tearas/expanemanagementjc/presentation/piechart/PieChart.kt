package com.tearas.expanemanagementjc.presentation.piechart

import android.content.Context
import android.util.Log
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tearas.expanemanagementjc.R
import com.tearas.expanemanagementjc.ui.theme.Blue
import com.tearas.expanemanagementjc.ui.theme.LightBlue
import com.tearas.expanemanagementjc.ui.theme.dynamicTextColor
import com.tearas.expanemanagementjc.ui.theme.isNightMode
import com.tearas.expanemanagementjc.utils.format
import java.text.DecimalFormat

val colorsChart = listOf(
    Blue,
    Color(0xFFFFC400),
    Color(0xFFCD44E4),
    Color(0xFF1DE9B6),
    Color(0xFFBBE621),
    LightBlue
)

fun topFourAndOthers(context: Context, data: Map<String, Double>): Map<String, Double> {
    val sortedEntries = data.entries.toMutableList()
    if (data.size > 5) {
        val topFour = sortedEntries.take(5).map { it.toPair() }.toMutableList()
        val remaining = sortedEntries.subList(5, data.size).sumOf { it.value }
        val itemOther = context.getString(R.string.other) to remaining
        topFour.add(itemOther)
        return topFour.toMap()
    }

    return data
}

@Composable
fun PieChart(
    data: Map<String, Double>,
    radiusOuter: Dp = 140.dp,
    chartBarWidth: Dp = 35.dp,
    animDuration: Int = 1000,
) {

    val totalSum = data.values.sum()
    val floatValue = mutableListOf<Float>()
    val context = LocalContext.current
    val newMap = topFourAndOthers(context, data)

    // To set the value of each Arc according to
    // the value given in the data, we have used a simple formula.
    // For a detailed explanation check out the Medium Article.
    // The link is in the about section and readme file of this GitHub Repository

    newMap.values.forEachIndexed { index, values ->
        val percentage = 360 * values.toFloat() / totalSum.toFloat()
        floatValue.add(index, percentage)
    }

    // add the colors as per the number of data(no. of pie chart entries)
    // so that each data will get a color
    var animationPlayed by remember { mutableStateOf(false) }

    var lastValue = 0f

    // it is the diameter value of the Pie
    val animateSize by animateFloatAsState(
        targetValue = if (animationPlayed) radiusOuter.value * 2f else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        )
    )

    // if you want to stabilize the Pie Chart you can use value -90f
    // 90f is used to complete 1/4 of the rotation
    val animateRotation by animateFloatAsState(
        targetValue = if (animationPlayed) -90f else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        )
    )

    // to play the animation only once when the function is Created or Recomposed
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Pie Chart using Canvas Arc
        Box(
            modifier = Modifier.size(animateSize.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(
                modifier = Modifier
                    .offset { IntOffset.Zero }
                    .size(radiusOuter * 2f)
                    .rotate(animateRotation)
            ) {

                floatValue.forEachIndexed { index, value ->
                    if (value != 0f) {
                        drawArc(
                            color = colorsChart[index],
                            startAngle = lastValue,
                            sweepAngle = value,  // Trừ đi khoảng cách để tạo khoảng trống
                            useCenter = false,
                            style = Stroke(chartBarWidth.toPx(), cap = StrokeCap.Butt)
                        )
                        lastValue += value
                    }
                }
            }
        }
    }
}

@Composable
fun DetailsPieChart(
    modifier: Modifier = Modifier,
    data: Map<String, Double>,
    colors: List<Color>
) {
    val totalValue = data.values.sum()
    val context = LocalContext.current
    val newMap = topFourAndOthers(context, data)
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
    ) {
        // create the data items
        newMap.values.forEachIndexed { index, value ->
            DetailsPieChartItem(
                data = Pair(data.keys.elementAt(index), value * 100.0 / totalValue),
                color = colors[index]
            )
        }
    }
}

@Composable
fun DetailsPieChartItem(
    data: Pair<String, Double>,
    height: Dp = 30.dp,
    color: Color
) {
    val gradientColors = listOf(color, Color.LightGray)

    Surface(
        modifier = Modifier,
        color = Color.Transparent
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(height),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(6.5f)
            ) {
                Box(
                    modifier = Modifier
                        .border(
                            brush = Brush.linearGradient(
                                colors = gradientColors
                            ),
                            width = 4.dp, // Độ rộng của đường viền
                            shape = CircleShape // Hình dạng của đường viền
                        )
                        .size(20.dp)
                )

                Text(
                    modifier = Modifier.padding(start = 15.dp),
                    text = data.first,
                    fontSize = 18.sp,
                    color = dynamicTextColor(),
                    overflow = TextOverflow.Ellipsis
                )
            }
            Text(
                modifier = Modifier
                    .weight(3.5f)
                    .fillMaxWidth(),
                text = DecimalFormat("#.##").format(data.second) + " %",
                fontSize = 18.sp,
                color = dynamicTextColor(),
                textAlign = TextAlign.End,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}