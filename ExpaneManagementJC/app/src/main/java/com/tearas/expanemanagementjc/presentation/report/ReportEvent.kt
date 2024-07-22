package com.tearas.expanemanagementjc.presentation.report

sealed class ReportEvent {
    data object StatisticalReport : ReportEvent()
}