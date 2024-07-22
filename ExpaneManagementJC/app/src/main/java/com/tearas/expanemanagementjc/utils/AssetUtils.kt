package com.tearas.expanemanagementjc.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tearas.expanemanagementjc.data.dto.CategoryImageSourceDto
import com.tearas.expanemanagementjc.domain.model.CategoryImage
import java.io.IOException

object AssetUtils {
    fun  getAssetCategoryFromJson(context: Context): List<CategoryImageSourceDto> {
        val stringJson = readFile(context, "category-en.json")
        val listType = object : TypeToken<List<CategoryImageSourceDto>>() {}.type
        return Gson().fromJson(stringJson, listType)
    }

    @Throws(IOException::class)
    private fun readFile(context: Context, fileName: String): String {
        return context.assets.open(fileName).bufferedReader().use { it.readText() }
    }
}