package com.tearas.expanemanagementjc.broadcast

import android.app.Service
import android.content.Intent
import android.os.IBinder

class AlarmService : Service() {
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        println("Alarm triggered: ")
        return super.onStartCommand(intent, flags, startId)
    }
}