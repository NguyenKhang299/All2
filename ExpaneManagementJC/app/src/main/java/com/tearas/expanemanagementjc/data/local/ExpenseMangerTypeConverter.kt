package com.tearas.expanemanagementjc.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import java.util.Date

@ProvidedTypeConverter
class ExpenseMangerTypeConverter {
    @TypeConverter
    fun dateToLong(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun longToDate(mili: Long): Date {
        return Date(mili)
    }
}