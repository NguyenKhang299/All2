package com.tearas.expanemanagementjc.domain.service

import com.tearas.expanemanagementjc.domain.model.RemindModel
import com.tearas.expanemanagementjc.domain.model.RepeatType

interface IAlarmManager {
    fun scheduleAlarm(remind: RemindModel, timeInMillis: Long, typeRepeat: RepeatType)
    fun cancelScheduleAlarm(id: Long)
}