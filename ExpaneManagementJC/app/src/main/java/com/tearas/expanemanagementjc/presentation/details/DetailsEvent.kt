package com.tearas.expanemanagementjc.presentation.details

sealed class DetailsEvent {
    data class GetDetails(val id: Long) : DetailsEvent()
    data class Delete(val id: Long) : DetailsEvent()
}