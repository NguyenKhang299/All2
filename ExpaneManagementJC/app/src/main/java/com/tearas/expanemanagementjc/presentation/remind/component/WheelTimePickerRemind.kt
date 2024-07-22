package com.tearas.expanemanagementjc.presentation.remind.component

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.commandiron.wheel_picker_compose.WheelTimePicker
import com.commandiron.wheel_picker_compose.core.TimeFormat
import com.commandiron.wheel_picker_compose.core.WheelPickerDefaults
import com.tearas.expanemanagementjc.domain.model.RemindModel
import com.tearas.expanemanagementjc.utils.formatTime
import com.tearas.expanemanagementjc.utils.parseTime
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.util.Calendar
import java.util.Date


@Composable
fun WheelTimePickerRemind(isUpdate: Boolean = false, time: Long, onTimeChanged: (Long) -> Unit) {
    var width by remember { mutableIntStateOf(0) }

    val calendar = Calendar.getInstance()
    calendar.timeInMillis = time

    if (isUpdate && time != 0L) {
        WheelTimePicker(
            startTime = LocalTime.of(calendar[Calendar.HOUR_OF_DAY], calendar[Calendar.MINUTE]),
            timeFormat = TimeFormat.AM_PM,
            textStyle = MaterialTheme.typography.titleLarge.copy(
                fontStyle = FontStyle.Normal,
                fontFamily = FontFamily.Default,
                fontSize = 28.sp
            ),
            selectorProperties = WheelPickerDefaults.selectorProperties(
                enabled = true,
                shape = RoundedCornerShape(0.dp),
                color = Color(0xFFf1faee).copy(alpha = 0.2f),
                border = BorderStroke(0.dp, Color(0xFFf1faee))
            ),
            size = DpSize(
                with(LocalDensity.current) { width.toDp() },
                200.dp
            ),
            modifier = Modifier
                .padding(top = 15.dp)
                .fillMaxWidth()
                .onGloballyPositioned { width = it.size.width }
        ) { snappedTime ->
            onTimeChanged(snappedTime.getCurrentDateAndTime().time)
        }
    }
    if (!isUpdate) {
        WheelTimePicker(
            startTime = LocalTime.now(),
            timeFormat = TimeFormat.AM_PM,
            textStyle = MaterialTheme.typography.titleLarge.copy(
                fontStyle = FontStyle.Normal,
                fontFamily = FontFamily.Default,
                fontSize = 28.sp
            ),
            selectorProperties = WheelPickerDefaults.selectorProperties(
                enabled = true,
                shape = RoundedCornerShape(0.dp),
                color = Color(0xFFf1faee).copy(alpha = 0.2f),
                border = BorderStroke(0.dp, Color(0xFFf1faee))
            ),
            size = DpSize(
                with(LocalDensity.current) { width.toDp() },
                200.dp
            ),
            modifier = Modifier
                .padding(top = 15.dp)
                .fillMaxWidth()
                .onGloballyPositioned { width = it.size.width }
        ) { snappedTime ->
            onTimeChanged(snappedTime.getCurrentDateAndTime().time)
        }
    }
}

fun LocalTime.getCurrentDateAndTime(): Date {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = System.currentTimeMillis()
    calendar[Calendar.HOUR_OF_DAY] = this.hour
    calendar[Calendar.MINUTE] = this.minute
    calendar[Calendar.SECOND] = 0
    calendar[Calendar.MILLISECOND] = 0
    return calendar.time
}
