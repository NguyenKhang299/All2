package com.tearas.expanemanagementjc.presentation.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.tearas.expanemanagementjc.R
import com.tearas.expanemanagementjc.presentation.common.AutoResizedText
import com.tearas.expanemanagementjc.ui.theme.ExpaneManagementJCTheme
import com.tearas.expanemanagementjc.ui.theme.WhiteGray
import com.tearas.expanemanagementjc.ui.theme.isNightMode
import com.tearas.expanemanagementjc.ui.theme.md_theme_dark_background_appbar

@Composable
fun TextFieldSearch(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit,
    onClickNavigation: () -> Unit,
    onClickDelete: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    var txtSearch by remember {
        mutableStateOf("")
    }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    val keyboard = LocalFocusManager.current
    ConstraintLayout(
        modifier = Modifier.fillMaxWidth(),
    ) {
        val (navIcon, edtSearch, btnSearch) = createRefs()
        IconButton(modifier = Modifier.constrainAs(navIcon) {
            start.linkTo(parent.start)
            centerVerticallyTo(parent)
        }, onClick = { onClickNavigation() }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                tint = if (isNightMode()) Color.White else Color.Black
            )
        }
        TextField(
            shape = RoundedCornerShape(15.dp),
            modifier = modifier
                .constrainAs(edtSearch) {
                    start.linkTo(navIcon.end)
                    end.linkTo(btnSearch.start)
                    width = Dimension.fillToConstraints
                    centerVerticallyTo(parent)
                }
                .focusRequester(focusRequester),
            value = txtSearch,
            onValueChange = { txtSearch = it },
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                focusedContainerColor = if (isNightMode()) md_theme_dark_background_appbar else WhiteGray.copy(
                    alpha = 0.5f
                ),
                unfocusedContainerColor = if (isNightMode()) md_theme_dark_background_appbar else WhiteGray.copy(
                    alpha = 0.5f
                )
            ),
            trailingIcon = {
                IconButton(onClick = {
                    txtSearch = ""
                    onClickDelete()
                }) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = null,
                        tint = if (isNightMode()) Color.White else Color.Black
                    )
                }
            },
            leadingIcon = {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = null,
                        tint = if (isNightMode()) Color.White else Color.Black
                    )
                }
            },
            keyboardActions = KeyboardActions(
                onSearch = {
                    keyboard.clearFocus()
                    onSearch(txtSearch)
                }
            ),
            keyboardOptions = KeyboardOptions(
                showKeyboardOnFocus = true,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            )
        )
        Button(
            modifier = Modifier.constrainAs(btnSearch) {
                end.linkTo(parent.end)
                centerVerticallyTo(parent)
            },
            onClick = {
                keyboard.clearFocus()
                onSearch(txtSearch)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            )
        ) {
            AutoResizedText(
                text = stringResource(id = R.string.search),
                color = Color.Red,
            )
        }
    }
}


@Preview
@Composable
private fun TextFieldSearchPreview() {
    ExpaneManagementJCTheme {
        var txtSearch by remember {
            mutableStateOf("")
        }

    }
}