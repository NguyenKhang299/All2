package com.tearas.expanemanagementjc.usecases.excel

import com.tearas.expanemanagementjc.domain.model.ProfitLoss
import com.tearas.expanemanagementjc.service.ExcelService

class ExportExcel(private val excelService: ExcelService) {
    suspend operator fun invoke(statisticalReport: Map<Int, List<ProfitLoss>>) =
        excelService.exportFileExcel(statisticalReport)
}