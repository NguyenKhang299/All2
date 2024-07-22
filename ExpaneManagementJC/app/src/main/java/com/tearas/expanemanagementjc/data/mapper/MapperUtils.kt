package com.tearas.expanemanagementjc.data.mapper

import com.tearas.expanemanagementjc.ExpenseApplication
import com.tearas.expanemanagementjc.data.dto.CategoryDto
import com.tearas.expanemanagementjc.data.dto.ExpenseDto
import com.tearas.expanemanagementjc.data.dto.StatisticalDto
import com.tearas.expanemanagementjc.domain.model.CategoryImage
import com.tearas.expanemanagementjc.domain.model.Expense
import com.tearas.expanemanagementjc.domain.model.Language
import com.tearas.expanemanagementjc.domain.model.ProfitLoss
import com.tearas.expanemanagementjc.domain.model.TransactionType
import com.tearas.expanemanagementjc.utils.formatParseDate
import com.tearas.expanemanagementjc.utils.getCategoryByLanguage
import com.tearas.expanemanagementjc.utils.getYearDate
import com.tearas.expanemanagementjc.utils.translatedCategoryTypeList
import java.util.Date

fun List<ExpenseDto>.mapToProfitLoss(): Map<ProfitLoss, List<ExpenseDto>> {
    val newExpenses = this.map { it.apply { date = it.date.formatParseDate() } }
    val expenseGroups = newExpenses.groupBy { it.date }.toMap()
    return expenseGroups.entries.associate { (date, expenses) ->
        val collect = expenses.filter { it.transactionType == TransactionType.COLLECT }
            .sumOf { it.expense }
        val spend = expenses.filter { it.transactionType == TransactionType.SPEND }
            .sumOf { it.expense }
        ProfitLoss(
            date = date,
            collect = collect,
            spend = spend,
            surplus = collect - spend
        ) to expenses.map {
            if (it.isDefault) it.copy(
                category = it.category.getCategoryByLanguage(
                    ExpenseApplication.LANGUAGE
                )
            ) else it
        }
    }
}

fun List<StatisticalDto>.mapCategories1(): List<StatisticalDto> {
    return this.map {
        if (it.isDefault) it.copy(
            category = it.category.getCategoryByLanguage(
                ExpenseApplication.LANGUAGE
            )
        ) else it
    }
}

fun List<CategoryDto>.mapCategories(): List<CategoryDto> {
    return this.map {
        if (it.isDefault) it.copy(
            category = it.category.getCategoryByLanguage(
                ExpenseApplication.LANGUAGE
            )
        ) else it
    }
}

fun List<ProfitLoss>.mapToReportExpense(): Map<Int, List<ProfitLoss>> {
    val newExpenses = this.map { it.apply { date = it.date.formatParseDate() } }
    return newExpenses.groupBy { it.date.getYearDate() }.toMap()
}

fun List<CategoryImage>.mapToCategoriesImage(): Map<String, List<CategoryImage>> {
    val map = this.groupBy { it.type }.toList().mapIndexed { index, pair ->
        val newKey =
            if (ExpenseApplication.LANGUAGE == Language.VI) translatedCategoryTypeList[index].first else translatedCategoryTypeList[index].second
        newKey to pair.second
    }.toMap()
    return map
}

fun List<ProfitLoss>.calculateTotalProfitLoss() = ProfitLoss(
    Date(),
    this.sumOf { it.collect },
    this.sumOf { it.spend },
    this.sumOf { it.collect - it.spend })

fun ExpenseDto.mapToExpense(): Expense {
    return Expense(
        expenseId = this.expenseId,
        categoryId = this.category_id,
        note = this.note,
        expense = this.expense,
        pathFile = this.pathFile,
        transactionType = this.transactionType,
        date = this.date
    )
}

fun ExpenseDto.mapToCategoryDto(): CategoryDto {
    return CategoryDto(
        category_id, category, pathIcon, transactionType
    )
}

fun List<StatisticalDto>.mapToDetailsPieChart() = this.map {
    it.category to it.totalExpenses
}.toMap()