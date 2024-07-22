package com.tearas.expanemanagementjc.presentation.add_edit_expense

import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tearas.expanemanagementjc.data.dto.CategoryDto
import com.tearas.expanemanagementjc.data.mapper.mapToExpense
import com.tearas.expanemanagementjc.domain.model.Expense
import com.tearas.expanemanagementjc.domain.model.TransactionType
import com.tearas.expanemanagementjc.usecases.account.AccountUseCases
import com.tearas.expanemanagementjc.usecases.category.CategoryUseCases
import com.tearas.expanemanagementjc.usecases.expense.ExpenseUseCases
import com.tearas.expanemanagementjc.utils.format
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AddExpenseViewModel @Inject constructor(
    private val expenseUseCases: ExpenseUseCases,
    private val categoryUseCases: CategoryUseCases,
    private val accountUseCases: AccountUseCases,
) : CalculatorViewModel() {

    var expenseState by mutableStateOf(StateAddExpense())
        private set

    var expenseObj = Expense()

    var note by mutableStateOf("")
        private set
    var expense by mutableStateOf("")
        private set
    var pathFile by mutableStateOf("")
        private set

    private fun onIdAccountChanged(idAccount: Long) {
        expenseObj = expenseObj.copy(accountId = idAccount)
    }

    fun onNoteChanged(note: String) {
        expenseObj = expenseObj.copy(note = note)
        this.note = note
    }

    fun onExpenseChanged(expense: String) {
        this.expense = expense
    }

    fun onCategoryChanged(categoryDto: CategoryDto) {
        expenseObj = expenseObj.copy(categoryId = categoryDto.category_id)
        expenseObj = expenseObj.copy(transactionType = categoryDto.transactionType)
    }

    fun onPathFileChanged(path: String) {
        pathFile = path
        expenseObj = expenseObj.copy(pathFile = path)
    }

    var selectCategoriesCollect by mutableStateOf<List<CategoryDto>>(emptyList())
        private set
    var selectCategoriesSpend by mutableStateOf<List<CategoryDto>>(emptyList())
        private set

    fun onEvent(event: EventAddExpense) {
        when (event) {
            is EventAddExpense.AddExpense -> insertExpense()
            is EventAddExpense.SelectCategoriesDtoCollect -> selectCategoriesCollect()
            is EventAddExpense.SelectCategoriesDtoSpend -> selectCategoriesSpend()
            is EventAddExpense.GetExpense -> getExpense(event.idExpense)
            is EventAddExpense.UpdateExpense -> updateExpense()
        }
    }

    private fun getExpense(id: Long) {
        viewModelScope.launch {
            val expense = expenseUseCases.selectExpense(id)
            expenseObj = expense.mapToExpense()
            onNoteChanged(expense.note)
            onPathFileChanged(expense.pathFile)
            this@AddExpenseViewModel.expense = BigDecimal(expense.expense).format()
            state = state.copy(number1 = this@AddExpenseViewModel.expense)
            expenseState = expenseState.copy(
                expense = expense.mapToExpense(),
                expenseDto = expense,
                isSuccess = null
            )
        }
    }

    private fun filterAndSortCategories(categories: List<CategoryDto>): List<CategoryDto> {
        return categories
            .filter { !it.isDelete }
            .sortedBy { it.isDefault }
            .toList()
    }

    private fun selectCategoriesCollect() {
        viewModelScope.launch {
            categoryUseCases.selectCategoriesDto(TransactionType.COLLECT).collect {
                selectCategoriesCollect = filterAndSortCategories(it)
            }
        }
    }

    private fun selectCategoriesSpend() {
        viewModelScope.launch {
            categoryUseCases.selectCategoriesDto(TransactionType.SPEND).collect {
                selectCategoriesSpend = filterAndSortCategories(it)
            }
        }
    }

    private fun updateOrInsertExpense(isUpdate: Boolean) {
        viewModelScope.launch {
            val value = expense.replace(",", "").replace(" ", "").toDouble()
            val cleanExpense =
                BigDecimal(value).setScale(2, RoundingMode.HALF_UP)

            val account = accountUseCases.selectAccountUsing()
            onIdAccountChanged(account.accountId)
            expenseState = expenseState.copy(
                expense = expenseObj.copy(
                    date = Date(System.currentTimeMillis()),
                    expense = cleanExpense.toDouble()
                ),
                isSuccess = null
            )
            if (isUpdate) {
                expenseUseCases.updateExpense(expenseState.expense!!)
            } else {
                expenseUseCases.insertExpense(expenseState.expense!!)
            }
            expenseState = expenseState.copy(isSuccess = true)
        }
    }

    private fun updateExpense() {
        updateOrInsertExpense(isUpdate = true)
    }

    private fun insertExpense() {
        updateOrInsertExpense(isUpdate = false)
    }

}