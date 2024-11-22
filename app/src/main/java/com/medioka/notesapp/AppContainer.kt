package com.medioka.notesapp

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.medioka.notesapp.data.NoteDatabase
import com.medioka.notesapp.data.NoteRepositoryImpl
import com.medioka.notesapp.domain.usecase.GetNotesUseCase
import com.medioka.notesapp.domain.usecase.ModifyNoteUseCase

class AppContainer(application: Application) {
    private val noteDatabase = NoteDatabase.getDatabase(application)
    private val noteRepository = NoteRepositoryImpl(noteDatabase.noteDao())

    val getNotesUseCase = GetNotesUseCase(noteRepository)
    val modifyNoteUseCase = ModifyNoteUseCase(noteRepository)
}
