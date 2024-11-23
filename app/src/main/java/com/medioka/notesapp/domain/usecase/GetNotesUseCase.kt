package com.medioka.notesapp.domain.usecase

import com.medioka.notesapp.domain.model.Note
import com.medioka.notesapp.domain.repository.NoteRepository
import com.medioka.notesapp.domain.model.ResultState
import kotlinx.coroutines.flow.Flow

class GetNotesUseCase(private val noteRepository: NoteRepository) {
    fun getNotes(): Flow<ResultState<List<Note>>> {
        return noteRepository.getNotes()
    }

    suspend fun getNoteDetail(id: Int): ResultState<Note> {
        return noteRepository.getNoteDetail(id)
    }
}
