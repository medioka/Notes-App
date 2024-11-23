package com.medioka.notesapp.domain

import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getNotes(): Flow<ResultState<List<Note>>>
    fun dummy(): Flow<List<Note>>
    suspend fun getNoteDetail(id: Int): ResultState<Note>
    suspend fun addNote(note: Note)
    suspend fun deleteNote(note: Note)
    suspend fun editNote(note: Note)
}
