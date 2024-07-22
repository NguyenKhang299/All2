package com.tearas.expanemanagementjc.presentation.add_edit_expense.component

data class Key(
    val key: String,
    val isSign: Boolean = false,
    val isDelete: Boolean = false,
    val isResult: Boolean = false
)

val listKeys = listOf(
    Key("7"),
    Key("8"),
    Key("9"),
    Key("/", isSign = true),
    Key("4"),
    Key("5"),
    Key("6"),
    Key("x", isSign = true),
    Key("1"),
    Key("2"),
    Key("3"),
    Key("-", isSign = true),
    Key("0"),
    Key(","),
    Key("", isDelete = true),
    Key("+", isSign = true),
    Key("=", isSign = true, isResult = true)
)