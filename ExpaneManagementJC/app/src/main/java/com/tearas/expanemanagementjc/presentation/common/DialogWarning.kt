package com.tearas.expanemanagementjc.presentation.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.tearas.expanemanagementjc.ui.theme.Blue
import com.tearas.expanemanagementjc.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogWarning(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    txtConfirm: String = stringResource(id = R.string.confirm),
    txtDisMiss: String = stringResource(id = R.string.cancel),
    tint: Color = Color.Black,
    colorConfirm: Color = Blue,
    colorDismiss: Color = Color.LightGray,
    icon: ImageVector = Icons.Filled.Warning,
) {
    AlertDialog(
        icon = {

        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text(txtConfirm, color = colorConfirm)
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(txtDisMiss, color = colorDismiss)
            }
        }
    )
}