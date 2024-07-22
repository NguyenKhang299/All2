package com.tearas.expanemanagementjc.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable

@Composable
fun checkPermission(
    onResult: (Boolean) -> Unit
): ManagedActivityResultLauncher<Array<String>, Map<String, @JvmSuppressWildcards Boolean>> {
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { isGranted ->
        onResult(isGranted.isNotEmpty() && isGranted.all { it.value })
    }
    return permissionLauncher
}

fun isGranted(context: Context, vararg permissions: String): Boolean =
    permissions.all { context.checkSelfPermission(it) == PackageManager.PERMISSION_GRANTED }

val CAMERA_PERMISSIONS = Manifest.permission.CAMERA
val READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
val POST_NOTIFICATIONS = Manifest.permission.POST_NOTIFICATIONS
