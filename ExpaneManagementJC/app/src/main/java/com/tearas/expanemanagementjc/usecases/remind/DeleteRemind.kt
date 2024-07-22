package com.tearas.expanemanagementjc.usecases.remind

import com.tearas.expanemanagementjc.data.repository.RemindRepositoryImpl
import com.tearas.expanemanagementjc.domain.model.RemindModel

class DeleteRemind(private val remindRepositoryImpl: RemindRepositoryImpl) {
    suspend operator fun invoke(remindModel: RemindModel) = remindRepositoryImpl.deleteRemind(remindModel)
}