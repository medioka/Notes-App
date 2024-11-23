package com.medioka.notesapp

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.medioka.notesapp.databinding.ActivityMainBinding
import com.medioka.notesapp.domain.model.Note
import com.medioka.notesapp.domain.model.ResultState
import com.medioka.notesapp.ui.HomeViewModel
import com.medioka.notesapp.ui.NoteListener
import com.medioka.notesapp.ui.NotesAdapter
import com.medioka.notesapp.ui.note.NoteActivity
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), NoteListener {
    private lateinit var homeViewModel: HomeViewModel
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
        homeViewModel = (application as NoteApplication).appContainer.getNotesViewModel(this)
        binding.rvNotes.layoutManager = GridLayoutManager(this, 2)

        handleAddNoteButton()
        observeNotes()
    }

    private fun observeNotes() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.noteResult.collect { state ->
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

    private fun handleAddNoteButton() {
        binding.fabAddNote.setOnClickListener {
            NoteActivity.launchActivity(this, NoteActivity.NO_ID)
        }
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
        NoteActivity.launchActivity(this, id)
    }

    override fun onDeleteClicked(note: Note) {
        homeViewModel.deleteNote(note)
    }
}

