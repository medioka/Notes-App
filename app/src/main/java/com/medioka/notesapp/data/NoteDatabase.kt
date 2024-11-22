package com.medioka.notesapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.medioka.notesapp.domain.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        private const val NOTE_DATABASE = "note_database"

        @Volatile
        private var NOTE_DB: NoteDatabase? = null

        fun getDatabase(context: Context): NoteDatabase {
            if (NOTE_DB != null) {
                return NOTE_DB as NoteDatabase
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    NOTE_DATABASE
                ).build()

                NOTE_DB = instance
                return NOTE_DB as NoteDatabase
            }

        }
    }
}
