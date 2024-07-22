package com.tearas.expanemanagementjc.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "category_image",
)
data class CategoryImage(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("category_image_id") val idCategoryImage: Long,
    @ColumnInfo("path_icon") val pathIcon: String,
    val type: CategoryType,
) : Serializable{
    override fun toString(): String {
        return "CategoryImage(idCategoryImage=$idCategoryImage, pathIcon='$pathIcon', type=$type)"
    }
}