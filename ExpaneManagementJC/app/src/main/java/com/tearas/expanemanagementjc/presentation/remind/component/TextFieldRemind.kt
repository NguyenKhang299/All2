package com.tearas.expanemanagementjc.presentation.remind.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun TextFieldRemind(
    modifier: Modifier = Modifier,
    titleId: Int,
    placeholderTextId: Int,
    value: String,
    onValueChange: (String) -> Unit
) {
    Column(modifier) {
        Text(
            text = stringResource(id = titleId),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = { onValueChange(it) },
            placeholder = { Text(text = stringResource(id = placeholderTextId)) }
        )
    }
}