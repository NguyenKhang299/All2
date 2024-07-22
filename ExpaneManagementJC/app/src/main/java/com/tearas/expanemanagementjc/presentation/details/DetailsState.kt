package com.tearas.expanemanagementjc.presentation.details

import com.tearas.expanemanagementjc.data.dto.ExpenseDto

data class DetailsState(
    val expenseDto: ExpenseDto? = null,
    val isDeleted: Boolean = false
    ) {
}