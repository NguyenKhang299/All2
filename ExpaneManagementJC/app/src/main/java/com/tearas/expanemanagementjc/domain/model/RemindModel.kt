package com.tearas.expanemanagementjc.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "RemindModel")
data class RemindModel(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    var title: String = "",
    var note: String = "",
    var time: Long = 0,
    var repeatType: RepeatType = RepeatType.ONCE,
    var isDeleteBeforeStart: Boolean = false
) : Serializable

enum class RepeatType {
    DAILY,
    WEEKLY,
    ONCE
}
