package com.tearas.expanemanagementjc.presentation.remind

import com.tearas.expanemanagementjc.domain.model.RemindModel

sealed class RemindEvent {
    data object GetReminds : RemindEvent()
    data class DeleteRemind(val remindModel: RemindModel) : RemindEvent()
}