package com.tearas.expanemanagementjc.presentation.add_edit_expense.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tearas.expanemanagementjc.R
import com.tearas.expanemanagementjc.ui.theme.Blue
import com.tearas.expanemanagementjc.ui.theme.ExpaneManagementJCTheme

@Composable
fun TextFieldAddExpense(
    input: String,
    badge: String = "",
    onValue: (String) -> Unit,
    onCLickEndIcon: () -> Unit,
    onCLickBadge: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    OutlinedTextField(
        label = {
            Text(text = context.getString(R.string.note))
        },
        trailingIcon = {

            BadgedBox(badge = {
                if (badge.isNotEmpty()) Image(
                    modifier = Modifier
                        .offset(x = (-6).dp, y = 7.dp)
                        .size(15.dp)
                        .clip(CircleShape)
                        .background(Color.Red)
                        .clickable { onCLickBadge() }
                        .padding(2.dp),
                    imageVector = Icons.Filled.Clear,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Color.White)
                )
            }) {
                IconButton(onClick = { onCLickEndIcon() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_camera_alt_24),
                        contentDescription = null,
                        tint = Blue
                    )
                }
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
            }
        ),
        value = input,
        onValueChange = {
            onValue(it)
        },
        modifier = Modifier
            .wrapContentSize(),
        colors = TextFieldDefaults.colors(
            disabledIndicatorColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent
        ),
        textStyle = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold
        ),
        singleLine = true,

        )
}

@Preview(showBackground = true)
@Composable
private fun TextFieldAddExpensePre() {
    ExpaneManagementJCTheme {
        TextFieldAddExpense(input = "", "", {}, {}) {

        }
    }
}