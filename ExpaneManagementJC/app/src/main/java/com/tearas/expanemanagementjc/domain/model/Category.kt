package com.tearas.expanemanagementjc.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "category", foreignKeys = [ForeignKey(
        entity = CategoryImage::class,
        childColumns = ["category_image_id"],
        parentColumns = ["category_image_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Category(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("category_id") val idCategory: Long,
    @ColumnInfo(name = "category_image_id") val categoryImageId: Long,
    @ColumnInfo(name = "transaction_type") val transactionType: TransactionType,
    @ColumnInfo(name = "category_type") val categoryType: CategoryType,
    val category: String,
    val isDefault: Boolean = false,
    val isDelete: Boolean = false
) : Serializable{
    override fun toString(): String {
        return "Category(idCategory=$idCategory, categoryImageId=$categoryImageId, transactionType=$transactionType, categoryType=$categoryType, category='$category', isDefault=$isDefault, isDelete=$isDelete)"
    }
}


