package com.tearas.expanemanagementjc.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.tearas.expanemanagementjc.domain.model.RemindModel
import kotlinx.coroutines.flow.Flow

@Dao
interface RemindDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRemind(remindModel: RemindModel): Long

    @Update
    suspend fun updateRemind(remindModel: RemindModel)

    @Delete
    suspend fun deleteRemind(remindModel: RemindModel)

    @Query("Select * from RemindModel")
     fun getReminds(): Flow<List<RemindModel>>

    @Query("Select * from RemindModel where id=:id")
    fun getRemind(id: Long): RemindModel
}