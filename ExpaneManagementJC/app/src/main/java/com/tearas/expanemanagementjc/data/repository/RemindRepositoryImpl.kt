package com.tearas.expanemanagementjc.data.repository

import android.os.SystemClock
import com.tearas.expanemanagementjc.data.local.RemindDAO
import com.tearas.expanemanagementjc.domain.model.RemindModel
import com.tearas.expanemanagementjc.domain.repository.IRemindRepository
import com.tearas.expanemanagementjc.service.AlarmManagerService
import kotlinx.coroutines.flow.Flow

class RemindRepositoryImpl(
    private val dao: RemindDAO,
    private val alarmManager: AlarmManagerService
) : IRemindRepository {

    override suspend fun insertRemind(remindModel: RemindModel) {
        val id = dao.insertRemind(remindModel)
        alarmManager.scheduleAlarm(
            remindModel.copy(id = id),
            remindModel.time,
            remindModel.repeatType
        )
    }

    override suspend fun updateRemind(remindModel: RemindModel) {
        alarmManager.scheduleAlarm(remindModel, remindModel.time, remindModel.repeatType)
        dao.updateRemind(remindModel)
    }

    override suspend fun deleteRemind(remindModel: RemindModel) {
        dao.deleteRemind(remindModel)
        alarmManager.cancelScheduleAlarm(remindModel.id)
    }

    override fun getReminds(): Flow<List<RemindModel>> {
        return dao.getReminds()
    }

    override suspend fun getRemind(id: Long): RemindModel {
        return dao.getRemind(id)
    }
}