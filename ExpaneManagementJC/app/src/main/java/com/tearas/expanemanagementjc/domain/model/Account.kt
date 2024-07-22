package com.tearas.expanemanagementjc.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "account")
data class Account(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "account_id") var accountId: Long,
    @ColumnInfo val name: String,
    @ColumnInfo var isUsing: Boolean = false
){
    override fun toString(): String {
        return "Account(accountId=$accountId, name='$name', isUsing=$isUsing)"
    }
}

