package com.tearas.expanemanagementjc.data.dto

import androidx.room.ColumnInfo
import com.tearas.expanemanagementjc.domain.model.TransactionType

data class CategoryDto(
    val category_id: Long = -1,
    val category: String = "",
    @ColumnInfo("path_icon") val pathIcon: String = "",
    @ColumnInfo(name = "transaction_type") val transactionType: TransactionType = TransactionType.SPEND,
    val isDefault: Boolean = false,
    val isDelete: Boolean = false
) {
    override fun toString(): String {
        return "CategoryDto(category_id=$category_id, category='$category', pathIcon='$pathIcon', transactionType=$transactionType)"
    }
}