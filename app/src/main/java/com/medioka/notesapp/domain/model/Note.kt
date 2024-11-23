package com.medioka.notesapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_note")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val content: String,
    val createdDate: String
)
