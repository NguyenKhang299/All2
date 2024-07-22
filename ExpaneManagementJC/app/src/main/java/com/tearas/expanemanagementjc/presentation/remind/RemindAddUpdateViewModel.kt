package com.tearas.expanemanagementjc.presentation.remind

import androidx.compose.runtime.getValue
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tearas.expanemanagementjc.domain.model.RemindModel
import com.tearas.expanemanagementjc.domain.model.RepeatType
import com.tearas.expanemanagementjc.usecases.remind.RemindUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RemindAddUpdateViewModel @Inject constructor(
    private val remindUseCases: RemindUseCases
) : ViewModel() {

    var state by mutableStateOf(RemindAddUpdateState())
        private set
    val note get() = state.remindModel.note
    val title get() = state.remindModel.title
    val isDelete get() = state.remindModel.isDeleteBeforeStart
    val validateRemind get() = state.remindModel.let { it.title.isNotEmpty() }

    private fun updateRemindModel(update: (RemindModel) -> RemindModel) {
        state = state.copy(remindModel = update(state.remindModel))
    }

    fun updateNote(note: String) {
        updateRemindModel { it.copy(note = note) }
    }

    fun updateDeleteBeforeStart(isDeleteBeforeStart: Boolean) {
        updateRemindModel { it.copy(isDeleteBeforeStart = isDeleteBeforeStart) }
    }

    fun updateRepeatType(repeatType: RepeatType) {
        updateRemindModel { it.copy(repeatType = repeatType) }
    }

    fun updateTitle(title: String) {
        updateRemindModel { it.copy(title = title) }
    }

    fun updateTime(time: Long) {
        updateRemindModel { it.copy(time = time) }
    }

    fun onEvent(event: RemindAddUpdateEvent) {
        when (event) {
            is RemindAddUpdateEvent.GetRemind -> getRemind(event.id)
            is RemindAddUpdateEvent.UpdateRemind -> updateRemind()
            is RemindAddUpdateEvent.AddRemind -> addRemind()
        }
    }

    private fun addRemind() {
        viewModelScope.launch {
            remindUseCases.insertRemind(state.remindModel)
        }
    }

    private fun updateRemind() {
        viewModelScope.launch {
            if (validateRemind) remindUseCases.updateRemind(state.remindModel)
        }
    }

    private fun getRemind(id: Long) {
        viewModelScope.launch {
            state = state.copy(remindModel = remindUseCases.getRemind(id))
        }
    }
}