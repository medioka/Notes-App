package com.medioka.notesapp.data

import com.medioka.notesapp.domain.model.Note
import com.medioka.notesapp.domain.model.ResultState
import com.medioka.notesapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class NoteRepositoryImpl(private val noteDao: NoteDao) : NoteRepository {
    override fun getNotes(): Flow<ResultState<List<Note>>> {
        return flow {
            emit(ResultState.Loading)
            noteDao.getNotes().collect { note ->
                if (note.isEmpty()) {
                    emit(ResultState.Empty)
                } else {
                    emit(ResultState.Success(note))
                }
            }
        }.catch { exception ->
            emit(ResultState.Error(exception))
        }
    }

    override suspend fun getNoteDetail(id: Int): ResultState<Note> {
        return try {
            val note = noteDao.getNoteDetail(id) ?: throw NullPointerException()
            ResultState.Success(note)
        } catch (e: Exception) {
            ResultState.Error(e)
        }
    }

    override suspend fun addNote(note: Note) {
        noteDao.addNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note)
    }

    override suspend fun editNote(note: Note) {
        noteDao.editNote(note)
    }
}
