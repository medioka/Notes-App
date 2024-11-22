package com.medioka.notesapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.medioka.notesapp.domain.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM user_note")
    fun getNotes(): Flow<List<Note>>

    @Query("SELECT * FROM user_note WHERE id = :id")
    suspend fun getNoteDetail(id: Int): Note

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Update
    suspend fun editNote(note: Note)
}
