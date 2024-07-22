package com.tearas.expanemanagementjc.presentation.add_edit_category.component

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.tearas.expanemanagementjc.R
import com.tearas.expanemanagementjc.ui.theme.ExpaneManagementJCTheme
import com.tearas.expanemanagementjc.ui.theme.LightBlue

@Composable
fun TextFieldAddCategory(
    modifier: Modifier = Modifier,
    text: String,
    onTextChanged: (String) -> Unit
) {

    OutlinedTextField(
        modifier = modifier,
        placeholder = {
            Text(
                text = stringResource(id = R.string.please_enter),
                color = Color.DarkGray
            )
        },
        value = text, onValueChange = {
            onTextChanged(it)
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = LightBlue,
            unfocusedContainerColor = LightBlue,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun TextFieldAddCategoryPreview() {
    ExpaneManagementJCTheme {
        TextFieldAddCategory(text = "") {}
    }
}