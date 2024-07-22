package com.tearas.expanemanagementjc.presentation.remind

import com.tearas.expanemanagementjc.domain.model.RemindModel

data class RemindAddUpdateState(
    val remindModel: RemindModel = RemindModel(),
    val isSuccess: Boolean? = null
) {
}