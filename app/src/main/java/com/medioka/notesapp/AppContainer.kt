package com.medioka.notesapp

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.medioka.notesapp.data.NoteDatabase
import com.medioka.notesapp.data.NoteRepositoryImpl
import com.medioka.notesapp.domain.usecase.GetNotesUseCase
import com.medioka.notesapp.domain.usecase.ModifyNoteUseCase

class AppContainer(application: Application) {
    private val noteDatabase = NoteDatabase.getInstance(application)
    private val noteRepository = NoteRepositoryImpl(noteDatabase.noteDao())

    private val getNotesUseCase = GetNotesUseCase(noteRepository)
    val modifyNoteUseCase = ModifyNoteUseCase(noteRepository)

    fun getNotesViewModel(owner: ViewModelStoreOwner): NotesViewModel {
        val notesViewModel =
            ViewModelProvider(
                owner = owner,
                factory = NotesViewModelFactory(getNotesUseCase, modifyNoteUseCase)
            )[NotesViewModel::class.java]

        return notesViewModel
    }
}
