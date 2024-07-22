package com.tearas.expanemanagementjc.usecases.remind

import com.tearas.expanemanagementjc.data.repository.RemindRepositoryImpl
import com.tearas.expanemanagementjc.domain.model.RemindModel

class GetReminds(private val remindRepositoryImpl: RemindRepositoryImpl) {
      operator fun invoke() = remindRepositoryImpl.getReminds()
}