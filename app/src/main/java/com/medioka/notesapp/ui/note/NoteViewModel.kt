package com.medioka.notesapp.ui.note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.medioka.notesapp.domain.model.Note
import com.medioka.notesapp.domain.model.ResultState
import com.medioka.notesapp.domain.usecase.GetNotesUseCase
import com.medioka.notesapp.domain.usecase.ModifyNoteUseCase
import com.medioka.notesapp.domain.utils.DateUtils
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NoteViewModel(
    getNotesUseCase: GetNotesUseCase,
    private val modifyNoteUseCase: ModifyNoteUseCase,
    private val id: Int
) : ViewModel() {
    private val _title = MutableStateFlow("")
    val title = _title.asStateFlow()

    private val _content = MutableStateFlow("")
    val content = _content.asStateFlow()

    private val _isOnEditMode = MutableStateFlow(true)
    val isOnEditMode = _isOnEditMode.asStateFlow()

    private val _isCreateNewNote = MutableStateFlow(true)

    init {
        viewModelScope.launch {
            val note = async { getNotesUseCase.getNoteDetail(id) }
            val result = note.await()
            if (result is ResultState.Success) {
                setTitle(result.data.title)
                setContent(result.data.content)
                setEditModeStatus(false)
                _isCreateNewNote.update { false }
            }
        }
    }

    fun setTitle(title: String) {
        _title.update { title }
    }

    fun setContent(content: String) {
        _content.update { content }
    }

    fun setEditModeStatus(condition: Boolean) {
        _isOnEditMode.update { condition }
    }

    fun submitNote() {
        viewModelScope.launch {
            if (_isCreateNewNote.value) {
                modifyNoteUseCase.addNote(title.value, content.value)
            } else {
                modifyNoteUseCase.editNote(
                    Note(
                        id = id,
                        title = title.value,
                        content = content.value,
                        createdDate = DateUtils.getCurrentDateInStringFormat()
                    )
                )
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class NoteViewModelFactory(
    private val getNotesUseCase: GetNotesUseCase,
    private val modifyNoteUseCase: ModifyNoteUseCase,
    private val id: Int
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NoteViewModel(getNotesUseCase, modifyNoteUseCase, id) as T
    }
}
