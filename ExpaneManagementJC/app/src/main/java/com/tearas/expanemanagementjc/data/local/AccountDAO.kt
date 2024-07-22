package com.tearas.expanemanagementjc.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tearas.expanemanagementjc.domain.model.Account
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAccount(account: Account)

    @Delete
    suspend fun deleteAccount(account: Account)

    @Query("Select * from account where account.isUsing=1")
    suspend fun selectAccount(): Account

    @Query("Select * from account")
    fun selectAccounts(): Flow<List<Account>>

}
