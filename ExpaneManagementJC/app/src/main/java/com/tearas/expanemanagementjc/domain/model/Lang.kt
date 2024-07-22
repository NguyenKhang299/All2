package com.tearas.expanemanagementjc.domain.model

sealed class Lang(val name: String) {
    data object VietNam : Lang("vn")
    data object English : Lang("en")
}