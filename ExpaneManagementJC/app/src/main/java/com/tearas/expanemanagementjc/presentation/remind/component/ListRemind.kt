package com.tearas.expanemanagementjc.presentation.remind.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LifecycleResumeEffect
import com.tearas.expanemanagementjc.domain.model.RemindModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListRemind(
    modifier: Modifier = Modifier,
    listRemind: List<RemindModel> = listOf(),
    onItemClick: (RemindModel) -> Unit,
    onItemDismiss: (RemindModel) -> Unit
) {
    val scope = rememberCoroutineScope()
    val list = remember(listRemind) {
        mutableStateListOf(*listRemind.toTypedArray())
    }

    LaunchedEffect(listRemind) {
        list.clear()
        list.addAll(listRemind)
    }
    LazyColumn(modifier.fillMaxWidth()) {
        itemsIndexed(listRemind, key = { _, listItem ->
            listItem.hashCode()
        }) { i, item ->
            val dismissState = rememberDismissState(
                confirmStateChange = {
                    if (it == DismissValue.DismissedToStart) {
                        list.remove(item)
                        onItemDismiss(item)
                        true
                    } else {
                        false
                    }
                }
            )

            SwipeToDismiss(
                state = dismissState,
                directions = setOf(DismissDirection.EndToStart),
                background = {
                    val direction = dismissState.dismissDirection ?: return@SwipeToDismiss
                    val alignment =
                        if (direction == DismissDirection.StartToEnd) Alignment.CenterStart else Alignment.CenterEnd
                    val icon = Icons.Default.Delete
                    val scale by animateFloatAsState(if (dismissState.targetValue == DismissValue.Default) 0.75f else 1.0f)

                    Box(
                        Modifier
                            .fillMaxSize()
                            .background(Color.Red)
                            .padding(horizontal = 20.dp),
                        contentAlignment = alignment
                    ) {
                        Icon(
                            icon,
                            contentDescription = "Delete Icon",
                            modifier = Modifier.scale(scale),
                            tint = Color.White
                        )
                    }
                },
                dismissContent = {
                    ItemRemind(remindModel = item) {
                        onItemClick(item)
                    }
                }
            )
        }
    }
}


@Preview
@Composable
private fun ListRemindPreview() {

}