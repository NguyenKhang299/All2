package com.tearas.expanemanagementjc.usecases.remind

import com.tearas.expanemanagementjc.data.repository.RemindRepositoryImpl

class GetRemind(private val remindRepositoryImpl: RemindRepositoryImpl) {
    suspend operator fun invoke(id: Long) = remindRepositoryImpl.getRemind(id)
}