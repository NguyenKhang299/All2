package com.tearas.expanemanagementjc.presentation.add_edit_expense.component

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tearas.expanemanagementjc.presentation.add_edit_expense.CalculatorAction
import com.tearas.expanemanagementjc.presentation.add_edit_expense.CalculatorOperation
import com.tearas.expanemanagementjc.presentation.add_edit_expense.CalculatorViewModel
import com.tearas.expanemanagementjc.ui.theme.ExpaneManagementJCTheme
import com.tearas.expanemanagementjc.utils.format
import java.math.BigDecimal

fun formatNumber(number: String): String {
    return if (number.contains(".")) {
        val splip = number.split(".")
        splip[0].toDouble().format() + "." + splip[1]
    } else {
        number.toDouble().format()
    }
}

@Composable
fun CalculatorExpense(
    valueDefault: String = "",
    modifier: Modifier = Modifier,
    onValue: (String) -> Unit
) {

    val viewModel = viewModel<CalculatorViewModel>()
    val state = viewModel.state
    var first by remember {
        mutableStateOf(false)
    }
   LaunchedEffect(state) {
       if (state.number1.toDoubleOrNull() != null && state.number2.toDoubleOrNull() != null) {
           val number1 = formatNumber(state.number1)
           val number2 = formatNumber(state.number2)
           onValue(number1 + (state.operation?.symbol ?: "") + number2)
       } else if (state.number1.toDoubleOrNull() != null) {
           val number1 = formatNumber(state.number1)
           onValue(number1 + (state.operation?.symbol ?: "") + state.number2)
       } else {
           onValue(state.number1 + (state.operation?.symbol ?: "") + state.number2)
       }
   }

    LazyColumn(
        modifier = modifier,
    ) {
        item {
            RowItem(listKeys = listKeys.subList(0, 4), onKeyClick = {
                handleCalculatorInput(it, viewModel)
            })
        }
        item {
            RowItem(listKeys = listKeys.subList(4, 8), onKeyClick = {
                handleCalculatorInput(it, viewModel)
            })
        }
        item {
            RowItem(listKeys = listKeys.subList(8, 12), onKeyClick = {
                handleCalculatorInput(it, viewModel)
            })
        }
        item {
            RowItem(true, listKeys = listKeys.subList(12, 17), onKeyClick = {
                handleCalculatorInput(it, viewModel)
            })
        }
    }

}

fun handleCalculatorInput(input: Key, viewModel: CalculatorViewModel) {
    if (input.isDelete) {
        viewModel.onAction(CalculatorAction.Delete)
        return
    }
    if (input.isResult) {
        viewModel.onAction(CalculatorAction.Calculate)
        return
    }
    when (input.key) {
        "+" -> viewModel.onAction(CalculatorAction.Operation(CalculatorOperation.Add))
        "-" -> viewModel.onAction(CalculatorAction.Operation(CalculatorOperation.Subtract))
        "x" -> viewModel.onAction(CalculatorAction.Operation(CalculatorOperation.Multiply))
        "/" -> viewModel.onAction(CalculatorAction.Operation(CalculatorOperation.Divide))
        "," -> viewModel.onAction(CalculatorAction.Decimal)
        else -> viewModel.onAction(CalculatorAction.Number(input.key.toInt()))
    }
}

@Composable
fun RowItem(isRowLast: Boolean = false, listKeys: List<Key>, onKeyClick: (Key) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth()) {
        for (i in listKeys.indices) {
            ItemKeypad(
                modifier = Modifier.weight(getWeightForIndex(isRowLast, i)),
                key = listKeys[i]
            ) {
                onKeyClick(it)
            }
        }
    }
}

fun getWeightForIndex(isRowLast: Boolean = false, index: Int): Float {
    return if (!isRowLast) {
        1f
    } else {
        val i = when (index) {
            0, 4 -> 2.5f
            1, 2 -> 1.5f
            else -> 2f
        }
        i
    }
}

@Preview
@Composable
private fun CalculatorExpensePreview() {
    ExpaneManagementJCTheme {

    }
}