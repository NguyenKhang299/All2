package com.tearas.expanemanagementjc.presentation.add_edit_expense.component

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.tearas.expanemanagementjc.R
import com.tearas.expanemanagementjc.ui.theme.ExpaneManagementJCTheme
import com.tearas.expanemanagementjc.utils.Const
import com.tearas.expanemanagementjc.utils.FileHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomPickImages(
    onDismissRequest: () -> Unit,
    onImagePicker: (String) -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var file = File("")
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            scope.launch(Dispatchers.IO) {
                imageUri = it
                val file = FileHelper.saveImageFile(context, uri)
                if (file != null) {
                    onImagePicker(file.path)
                    onDismissRequest()
                }
            }
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { isSuccess: Boolean ->
        if (isSuccess) {
            imageUri?.let {
                onImagePicker(file.path)
                onDismissRequest()
            }
        }
    }

    ModalBottomSheet(onDismissRequest = onDismissRequest) {
        BottomSheetOption(
            text = context.getString(R.string.gallery),
            icon = R.drawable.baseline_image_24
        ) {
            galleryLauncher.launch("image/*")
        }
        BottomSheetOption(
            text = context.getString(R.string.camera),
            icon = R.drawable.baseline_camera_alt_24
        ) {
            file = FileHelper.createFileToSave(context)
            imageUri = FileProvider.getUriForFile(context, Const.AUTH, file)
            cameraLauncher.launch(imageUri!!)
        }
    }
}

@Composable
fun BottomSheetOption(
    text: String,
    @DrawableRes icon: Int? = null,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(15.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = text)
        if (icon != null) Icon(
            painter = painterResource(id = icon),
            contentDescription = null
        )
    }
}


@Preview
@Composable
private fun BottomPickImagesPreview() {
    ExpaneManagementJCTheme {
    }
}