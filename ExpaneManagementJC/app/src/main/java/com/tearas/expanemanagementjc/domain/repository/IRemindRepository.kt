package com.tearas.expanemanagementjc.domain.repository

import com.tearas.expanemanagementjc.domain.model.RemindModel
import kotlinx.coroutines.flow.Flow

interface IRemindRepository {
    suspend fun insertRemind(remindModel: RemindModel)
    suspend fun updateRemind(remindModel: RemindModel)
    suspend fun deleteRemind(remindModel: RemindModel)
      fun getReminds(): Flow<List<RemindModel>>
    suspend fun getRemind(id: Long): RemindModel
}