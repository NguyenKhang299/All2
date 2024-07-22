package com.tearas.expanemanagementjc.data.dto

import androidx.room.ColumnInfo
import androidx.room.TypeConverters
import com.tearas.expanemanagementjc.data.local.ExpenseMangerTypeConverter
import com.tearas.expanemanagementjc.domain.model.TransactionType
import java.util.Date

data class ExpenseDto(
    var expenseId: Long = -1,
    val category_id: Long = -1,
    val category: String = "",
    val note: String = "",
    val expense: Double = 0.0,
    val isDefault: Boolean = false,
    @ColumnInfo(name = "path_file") val pathFile: String = "",
    @ColumnInfo(name = "path_icon") val pathIcon: String = "",
    @ColumnInfo(name = "transaction_type") val transactionType: TransactionType = TransactionType.SPEND,
    @TypeConverters(ExpenseMangerTypeConverter::class) var date: Date = Date()
)