package com.tearas.expanemanagementjc.presentation.setting.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tearas.expanemanagementjc.R
import com.tearas.expanemanagementjc.presentation.add_edit_expense.component.BottomSheetOption
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetOptionsPass(onRemove: () -> Unit, onDismissRequest: () -> Unit) {
    val sheetState = rememberModalBottomSheetState()
    val corou = rememberCoroutineScope()
    ModalBottomSheet(onDismissRequest = { onDismissRequest() }, sheetState = sheetState) {
        Text(
            text = stringResource(id = R.string.remove_password),
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onRemove()
                    corou.launch { sheetState.hide() }
                }
                .padding(15.dp), textAlign = TextAlign.Center
        )
    }
}   