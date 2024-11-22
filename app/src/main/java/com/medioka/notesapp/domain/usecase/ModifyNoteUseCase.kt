package com.medioka.notesapp.domain.usecase

import com.medioka.notesapp.domain.Note
import com.medioka.notesapp.domain.NoteRepository
import com.medioka.notesapp.domain.utils.DateUtils

class ModifyNoteUseCase(private val noteRepository: NoteRepository) {
    suspend fun addNote(title: String, content: String) {
        val newNote = Note(
            title = title,
            content = content,
            createdDate = DateUtils.getCurrentDateInStringFormat()
        )

        if (content.isEmpty()) {
            return
        }

        noteRepository.addNote(newNote)
    }

    suspend fun deleteNote(note: Note) {
        noteRepository.deleteNote(note)
    }

    suspend fun editNote(note: Note) {
        noteRepository.editNote(note)
    }
}
