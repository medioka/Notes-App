package com.medioka.notesapp.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_note")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val content: String,
    val createdDate: String
)
