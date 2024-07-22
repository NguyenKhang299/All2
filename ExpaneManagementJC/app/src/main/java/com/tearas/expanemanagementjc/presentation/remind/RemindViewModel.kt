package com.tearas.expanemanagementjc.presentation.remind

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tearas.expanemanagementjc.R
import com.tearas.expanemanagementjc.domain.model.RemindModel
import com.tearas.expanemanagementjc.usecases.remind.RemindUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RemindViewModel @Inject constructor(private val remindUseCases: RemindUseCases) :
    ViewModel() {
    var sate by mutableStateOf(RemindState())

    init {
        onEvent(RemindEvent.GetReminds)
    }

    fun onEvent(event: RemindEvent) {
        when (event) {
            is RemindEvent.GetReminds -> getReminds()
            is RemindEvent.DeleteRemind -> deleteRemind(event.remindModel)
        }
    }

    private fun deleteRemind(remindModel: RemindModel) {
        viewModelScope.launch {
            remindUseCases.deleteRemind(remindModel)
        }
    }

    private fun getReminds() {
        viewModelScope.launch {
            remindUseCases.getReminds().collect {
                sate = sate.copy(reminds = it)
            }
        }
    }
}