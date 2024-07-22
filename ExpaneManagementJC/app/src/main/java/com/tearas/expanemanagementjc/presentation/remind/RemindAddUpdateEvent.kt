package com.tearas.expanemanagementjc.presentation.remind

import com.tearas.expanemanagementjc.domain.model.RemindModel

sealed class RemindAddUpdateEvent {
    data class GetRemind(val id: Long) : RemindAddUpdateEvent()
    data object AddRemind  : RemindAddUpdateEvent()
    data object UpdateRemind  : RemindAddUpdateEvent()
}