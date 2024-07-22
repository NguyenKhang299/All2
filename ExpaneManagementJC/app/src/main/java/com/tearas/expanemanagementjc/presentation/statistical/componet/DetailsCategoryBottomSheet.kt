package com.tearas.expanemanagementjc.presentation.statistical.componet

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tearas.expanemanagementjc.presentation.statistical.StatisticalViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsCategoryBottomSheet(viewModel: StatisticalViewModel, onDismissRequest: () -> Unit) {
    ModalBottomSheet(onDismissRequest = { onDismissRequest() }) {
        ListItemStatistical(
            modifier = Modifier.padding(top = 20.dp),
            maxValue = viewModel.maxValueDetailsCate,
            isShowDate = true,
            data = viewModel.state.detailsCategory
        ) {

        }
    }
}