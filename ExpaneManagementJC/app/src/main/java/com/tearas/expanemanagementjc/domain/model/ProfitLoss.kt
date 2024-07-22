package com.tearas.expanemanagementjc.domain.model

import java.util.Date

data class ProfitLoss(
    var date: Date = Date(),
    val collect: Double = 0.0,
    val spend: Double = 0.0,
    val surplus: Double = collect - spend
)