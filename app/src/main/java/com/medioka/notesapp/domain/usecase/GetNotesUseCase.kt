package com.medioka.notesapp.domain.usecase

import com.medioka.notesapp.domain.Note
import com.medioka.notesapp.domain.NoteRepository
import com.medioka.notesapp.domain.ResultState
import kotlinx.coroutines.flow.Flow

class GetNotesUseCase(private val noteRepository: NoteRepository) {
    fun getNotes(): Flow<ResultState<List<Note>>> {
        return noteRepository.getNotes()
    }

    suspend fun getNoteDetail(id: Int): ResultState<Note> {
        return noteRepository.getNoteDetail(id)
    }
}
