package com.tearas.expanemanagementjc.domain.service

import com.tearas.expanemanagementjc.domain.model.ProfitLoss

interface IExcel {
    suspend fun exportFileExcel(statisticalReport: Map<Int, List<ProfitLoss>>): String?
}