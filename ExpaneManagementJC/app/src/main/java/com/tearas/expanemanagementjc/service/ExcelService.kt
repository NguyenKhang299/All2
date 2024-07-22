package com.tearas.expanemanagementjc.service

import android.content.Context
import android.media.MediaScannerConnection
import android.os.Build
import android.os.Environment
import android.util.Log
import com.tearas.expanemanagementjc.ExpenseApplication
import com.tearas.expanemanagementjc.R
import com.tearas.expanemanagementjc.domain.model.Language
import com.tearas.expanemanagementjc.domain.model.ProfitLoss
import com.tearas.expanemanagementjc.domain.service.IExcel
import com.tearas.expanemanagementjc.utils.getMonthDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.poi.ss.usermodel.HorizontalAlignment
import org.apache.poi.ss.usermodel.VerticalAlignment
import org.apache.poi.ss.util.CellRangeAddress
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream

class ExcelService(private val context: Context) : IExcel {
    override suspend fun exportFileExcel(
        statisticalReport: Map<Int, List<ProfitLoss>>
    ): String? {
        try {
            val workbook = XSSFWorkbook()

            val sheet = workbook.createSheet("Expense managers")
            val styleHeader = workbook.createCellStyle()
            styleHeader.alignment = HorizontalAlignment.CENTER
            styleHeader.verticalAlignment = VerticalAlignment.CENTER
            val styleDetails = workbook.createCellStyle()
            styleDetails.alignment = HorizontalAlignment.CENTER
            styleDetails.verticalAlignment = VerticalAlignment.CENTER
            val boldFont = workbook.createFont()
            boldFont.bold = true
            styleHeader.setFont(boldFont)
            val documentsDirectory =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)

            if (!documentsDirectory.exists()) {
                documentsDirectory.mkdirs()
            }

            val fileName = "report_${System.currentTimeMillis()}.xlsx"
            val file = File(documentsDirectory, fileName)
            val listColumn  = if (ExpenseApplication.LANGUAGE==Language.EN) {
                listOf("Year","Month","Expense","Income","Surplus")
            }else{
                listOf("Năm","Tháng","Chi phí","Thu nhập","Số dư")
            }

            // Tạo hàng tiêu đề
            val headerRow = sheet.createRow(0)
            listColumn.forEachIndexed { index, name ->
                val column = headerRow.createCell(index)
                column.setCellValue(name)
                column.cellStyle = styleHeader
            }

            var rowIndex = 1
            statisticalReport.forEach { (year, profitLosses) ->
                if (profitLosses.size - 1 > 0) {
                    val mergedRegion =
                        CellRangeAddress(rowIndex, rowIndex + profitLosses.size - 1, 0, 0)
                    sheet.addMergedRegion(mergedRegion)
                }

                profitLosses.forEachIndexed { index, profitLoss ->
                    val row = sheet.createRow(rowIndex + index)

                    row.createCell(0).setCellValue(year.toString())
                    row.createCell(1).setCellValue(profitLoss.date.getMonthDate())
                    row.createCell(2).setCellValue(profitLoss.spend)
                    row.createCell(3).setCellValue(profitLoss.collect)
                    row.createCell(4).setCellValue(profitLoss.surplus)
                    (0..4).forEach {
                        val style = if (it == 0) {
                            styleHeader
                        } else {
                            styleDetails
                        }
                        row.getCell(it).cellStyle = style
                    }
                }
                rowIndex += profitLosses.size
            }

            withContext(Dispatchers.IO) {
                val fileOut = FileOutputStream(file)
                workbook.write(fileOut)
                fileOut.flush()
                fileOut.close()
                workbook.close()
            }

            MediaScannerConnection.scanFile(
                context, arrayOf(file.toString()),
                null, null
            )
            return file.path
        } catch (e: Exception) {
            return null
        }
    }
}