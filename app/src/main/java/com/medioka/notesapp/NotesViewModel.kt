package com.medioka.notesapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.medioka.notesapp.domain.ResultState
import com.medioka.notesapp.domain.usecase.GetNotesUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class NotesViewModel(getNotesUseCase: GetNotesUseCase) : ViewModel() {
    val noteResult = getNotesUseCase.getNotes()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = ResultState.Default
        )
}

@Suppress("UNCHECKED_CAST")
class NotesViewModelFactory(private val getNotesUseCase: GetNotesUseCase): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
       return NotesViewModel(getNotesUseCase) as T
    }
}
