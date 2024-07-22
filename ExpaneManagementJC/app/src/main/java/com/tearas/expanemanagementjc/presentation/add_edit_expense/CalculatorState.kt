package com.tearas.expanemanagementjc.presentation.add_edit_expense

data class CalculatorState(
    val number1: String = "",
    val number2: String = "",
    val operation: CalculatorOperation? = null
)