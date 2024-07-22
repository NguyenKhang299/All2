package com.tearas.expanemanagementjc.presentation.common

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tearas.expanemanagementjc.ui.theme.Blue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogDatePicker(
    onDismissRq: () -> Unit,
    onConfirm: (Long) -> Unit
) {
    val datePickerState = rememberDatePickerState()
    datePickerState.displayMode = DisplayMode.Picker
    DatePickerDialog(onDismissRequest = {
        onDismissRq()
    }, dismissButton = {
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),
            onClick = {
                onDismissRq()
            },
        ) {
            Text(text = "Cancel", color = Color.Gray)
        }
    }, confirmButton = {
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),
            onClick = {
                datePickerState.selectedDateMillis?.let { onConfirm(it) }
            },
        ) {
            Text(text = "Confirm", color = Blue)

        }
    }) {
        DatePicker(
            showModeToggle = false,
            colors = DatePickerDefaults.colors(
                selectedDayContainerColor = Blue,
                selectedDayContentColor = Color.White,
                selectedYearContainerColor = Blue,
                todayDateBorderColor = Color.Transparent,
                currentYearContentColor = Blue,
//                headlineContentColor = Blue,
//                titleContentColor = Blue,
//                subheadContentColor = Blue,
                dateTextFieldColors = TextFieldDefaults.colors(),
            ),
            state = datePickerState,
        )
    }
}

@Preview
@Composable
private fun DialogDatePickerPreview() {
    DialogDatePicker({}, {})
}