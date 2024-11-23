package com.medioka.notesapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.medioka.notesapp.databinding.CardNoteBinding
import com.medioka.notesapp.domain.model.Note

class NotesAdapter(
    private val notes: List<Note>,
    private val noteListener: NoteListener
) :
    RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding = CardNoteBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val note = notes[position]
        with(holder) {
            binding.tvNoteTitle.text = note.title
            binding.tvNoteContent.text = note.content
            binding.tvNoteCreatedDate.text = note.createdDate
        }

        holder.binding.apply {
            btnDelete.setOnClickListener {
                noteListener.onDeleteClicked(note)
            }
            root.setOnClickListener {
                noteListener.onNoteClicked(note.id  )
            }
        }
    }

    inner class NotesViewHolder(val binding: CardNoteBinding) :
        RecyclerView.ViewHolder(binding.root)
}

interface NoteListener {
    fun onNoteClicked(id: Int)
    fun onDeleteClicked(note: Note)
}


