package com.medioka.notesapp

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.medioka.notesapp.data.NoteDatabase
import com.medioka.notesapp.data.NoteRepositoryImpl
import com.medioka.notesapp.domain.usecase.GetNotesUseCase
import com.medioka.notesapp.domain.usecase.ModifyNoteUseCase
import com.medioka.notesapp.ui.HomeViewModel
import com.medioka.notesapp.ui.NotesViewModelFactory
import com.medioka.notesapp.ui.note.NoteViewModel
import com.medioka.notesapp.ui.note.NoteViewModelFactory

class AppContainer(application: Application) {
    private val noteDatabase = NoteDatabase.getInstance(application)
    private val noteRepository = NoteRepositoryImpl(noteDatabase.noteDao())

    private val getNotesUseCase = GetNotesUseCase(noteRepository)
    private val modifyNoteUseCase = ModifyNoteUseCase(noteRepository)

    fun getNotesViewModel(owner: ViewModelStoreOwner): HomeViewModel {
        val homeViewModel =
            ViewModelProvider(
                owner = owner,
                factory = NotesViewModelFactory(getNotesUseCase, modifyNoteUseCase)
            )[HomeViewModel::class.java]

        return homeViewModel
    }

    fun getNoteViewModel(owner: ViewModelStoreOwner, id: Int): NoteViewModel {
        val noteViewModel =
            ViewModelProvider(
                owner = owner,
                factory = NoteViewModelFactory(getNotesUseCase, modifyNoteUseCase, id)
            )[NoteViewModel::class.java]

        return noteViewModel
    }
}
