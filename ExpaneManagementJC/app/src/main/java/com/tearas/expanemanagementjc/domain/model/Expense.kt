package com.tearas.expanemanagementjc.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.Date

@Entity(
    tableName = "expense",
    foreignKeys = [ForeignKey(
        entity = Account::class,
        childColumns = ["account_id"],
        parentColumns = ["account_id"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        Category::class, childColumns = ["category_id"],
        parentColumns = ["category_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Expense(
    @PrimaryKey(autoGenerate = true) var expenseId: Long = 0,
    @ColumnInfo(name = "account_id") val accountId: Long = 0,
    @ColumnInfo(name = "category_id") val categoryId: Long = 0,
    @ColumnInfo(name = "note") val note: String = "",
    @ColumnInfo val expense: Double = 0.0,
    @ColumnInfo(name = "path_file") val pathFile: String = "",
    @ColumnInfo(name = "transaction_type") val transactionType: TransactionType = TransactionType.SPEND,
    @ColumnInfo val date: Date = Date()
) : Serializable {
    override fun toString(): String {
        return "Expense(expenseId=$expenseId, accountId=$accountId, categoryId=$categoryId, note='$note', expense=$expense, pathFile='$pathFile', transactionType=$transactionType, date=$date)"
    }
}

