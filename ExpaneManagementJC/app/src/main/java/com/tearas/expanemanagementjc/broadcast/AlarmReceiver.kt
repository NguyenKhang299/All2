package com.tearas.expanemanagementjc.broadcast

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.tearas.expanemanagementjc.R
import com.tearas.expanemanagementjc.domain.model.RemindModel
import com.tearas.expanemanagementjc.presentation.MainActivity
import com.tearas.expanemanagementjc.usecases.remind.RemindUseCases
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@EntryPoint
@InstallIn(SingletonComponent::class)
interface BroadcastReceiverEntryPoint {
    fun remindUseCases(): RemindUseCases
}

class AlarmReceiver : BroadcastReceiver() {
    private val coroutine = CoroutineScope(Dispatchers.IO)

    override fun onReceive(context: Context, intent: Intent) {

        val remind = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.extras!!.getSerializable("remind", RemindModel::class.java)
        } else {
            intent.extras!!.getSerializable("remind") as RemindModel
        }!!
        println("Alarm triggered: $remind")
        with(context) {
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notificationIntent = Intent(this@with, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(
                this@with,
                0,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            val notification = NotificationCompat.Builder(this@with, "2992003")
                .setContentTitle(remind.title)
                .setContentText(remind.note)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSmallIcon(R.drawable.baseline_camera_alt_24)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()

            notificationManager.notify(remind.id.toInt(), notification)
        }
        val entryPoint = EntryPointAccessors.fromApplication(
            context,
            BroadcastReceiverEntryPoint::class.java
        )
        val remindUseCases = entryPoint.remindUseCases()

        coroutine.launch { if (remind.isDeleteBeforeStart) remindUseCases.deleteRemind(remind) }

    }
}
