package com.medioka.notesapp

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.medioka.notesapp.databinding.ActivityMainBinding
import com.medioka.notesapp.domain.Note
import com.medioka.notesapp.domain.ResultState
import com.medioka.notesapp.ui.NoteListener
import com.medioka.notesapp.ui.NotesAdapter
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), NoteListener {
    private lateinit var notesViewModel: NotesViewModel
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        notesViewModel = (application as NoteApplication).appContainer.getNotesViewModel(this)
        binding.rvNotes.layoutManager = GridLayoutManager(this, 2)

        handleAddNoteClick()
        observeNotes()
    }

    private fun observeNotes() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                notesViewModel.noteResult.collect { state ->
                    when (state) {
                        is ResultState.Empty -> {
                            resetAllVisibility()
                            binding.viewEmpty.root.visibility = View.VISIBLE
                        }

                        is ResultState.Success -> {
                            resetAllVisibility()
                            binding.rvNotes.visibility = View.VISIBLE
                            addDataToRecyclerView(state.data)
                        }

                        is ResultState.Error -> {
                            resetAllVisibility()
                            binding.viewError.root.visibility = View.VISIBLE
                        }

                        is ResultState.Loading -> {
                            resetAllVisibility()
                            binding.viewLoading.root.visibility = View.VISIBLE
                        }

                        else -> Unit
                    }
                }
            }
        }
    }

    private fun handleAddNoteClick() {
        //TODO: later
    }

    private fun addDataToRecyclerView(notes: List<Note>) {
        val adapter = NotesAdapter(notes, this)
        binding.rvNotes.adapter = adapter
    }

    private fun resetAllVisibility() {
        binding.viewError.root.visibility = View.GONE
        binding.viewLoading.root.visibility = View.GONE
        binding.viewError.root.visibility = View.GONE
        binding.viewEmpty.root.visibility = View.GONE
        binding.rvNotes.visibility = View.GONE
    }

    override fun onNoteClicked(id: Int) {
        //TODO: Get to detailed note
    }

    override fun onDeleteClicked(note: Note) {
        notesViewModel.deleteNote(note)
    }

}

