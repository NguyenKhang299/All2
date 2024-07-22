package com.tearas.expanemanagementjc.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream


object FileHelper {
    fun openFile(context: Context, path: String) {
        val intent = Intent(
            Intent.ACTION_VIEW, FileProvider.getUriForFile(
                context,
                Const.AUTH,
                File(path)
            )
        )
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        context.startActivity(intent)

    }

    fun createFileToSave(context: Context): File {
        val directory = File(context.filesDir, "images")
        if (!directory.exists()) {
            directory.mkdirs()
        }
        return File(directory, "${System.currentTimeMillis()}.jpeg")
    }

    fun getUri(context: Context, path: String): Uri = FileProvider.getUriForFile(
        context, Const.AUTH,
        File(path)
    )

    suspend fun saveImageFile(context: Context, uri: Uri): File? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            val file = createFileToSave(context)
            val fileOutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fileOutputStream)
            fileOutputStream.flush()
            fileOutputStream.close()
            file
        } catch (e: Exception) {
            null
        }
    }

    fun getFilePathFromUri(uri: Uri, context: Context): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(uri, projection, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                return it.getString(columnIndex)
            }
        }
        return null
    }

}