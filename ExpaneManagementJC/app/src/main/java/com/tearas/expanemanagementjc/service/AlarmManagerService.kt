package com.tearas.expanemanagementjc.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.tearas.expanemanagementjc.broadcast.AlarmReceiver
import com.tearas.expanemanagementjc.domain.model.RemindModel
import com.tearas.expanemanagementjc.domain.model.RepeatType
import com.tearas.expanemanagementjc.domain.service.IAlarmManager
import dagger.hilt.android.qualifiers.ApplicationContext
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.util.Locale
import javax.inject.Inject

class AlarmManagerService @Inject constructor(@ApplicationContext val context: Context) :
    IAlarmManager {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    override fun scheduleAlarm(remind: RemindModel, timeInMillis: Long, typeRepeat: RepeatType) {
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra("remind", remind)
        val interval = when (typeRepeat) {
            RepeatType.DAILY -> AlarmManager.INTERVAL_DAY
            RepeatType.WEEKLY -> AlarmManager.INTERVAL_DAY * 7
            RepeatType.ONCE -> 0L
        }

        val time =
            if (timeInMillis < System.currentTimeMillis()) timeInMillis + (24 * 60 * 60 * 1000) else timeInMillis
        if (typeRepeat == RepeatType.ONCE) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                time,
                PendingIntent.getBroadcast(
                    context,
                    remind.id.toInt(),
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            )
        } else {
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                time,
                interval,
                PendingIntent.getBroadcast(
                    context,
                    remind.id.toInt(),
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            )
        }
    }

    override fun cancelScheduleAlarm(id: Long) {
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            id.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }
}
