package com.medioka.notesapp.domain.repository

import com.medioka.notesapp.domain.model.ResultState
import com.medioka.notesapp.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getNotes(): Flow<ResultState<List<Note>>>
    suspend fun getNoteDetail(id: Int): ResultState<Note>
    suspend fun addNote(note: Note)
    suspend fun deleteNote(note: Note)
    suspend fun editNote(note: Note)
}
