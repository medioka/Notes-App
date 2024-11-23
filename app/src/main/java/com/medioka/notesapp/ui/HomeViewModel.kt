package com.medioka.notesapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.medioka.notesapp.domain.model.Note
import com.medioka.notesapp.domain.model.ResultState
import com.medioka.notesapp.domain.usecase.GetNotesUseCase
import com.medioka.notesapp.domain.usecase.ModifyNoteUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    getNotesUseCase: GetNotesUseCase,
    private val modifyNoteUseCase: ModifyNoteUseCase
) : ViewModel() {
    val noteResult = getNotesUseCase.getNotes().distinctUntilChanged()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = ResultState.Default
        )

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            modifyNoteUseCase.deleteNote(note)
        }
    }
}

@Suppress("UNCHECKED_CAST")
class NotesViewModelFactory(
    private val getNotesUseCase: GetNotesUseCase,
    private val modifyNoteUseCase: ModifyNoteUseCase
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(getNotesUseCase, modifyNoteUseCase) as T
    }
}
