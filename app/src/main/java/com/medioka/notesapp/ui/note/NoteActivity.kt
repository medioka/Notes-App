package com.medioka.notesapp.ui.note

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.medioka.notesapp.NoteApplication
import com.medioka.notesapp.R
import com.medioka.notesapp.databinding.ActivityNoteBinding
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class NoteActivity : AppCompatActivity() {
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var binding: ActivityNoteBinding
    private var id: Int = -1

    private fun getBundle() {
        val bundle = intent.extras

        if (bundle != null) {
            id = bundle.getInt(ID_KEY)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        getBundle()

        binding = ActivityNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        noteViewModel = (application as NoteApplication).appContainer.getNoteViewModel(this, id)

        observerEditButton()
        observerTitleAndContentInitialValue()
        listenerTitleAndContent()
    }

    private fun observerEditButton() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                noteViewModel.isOnEditMode.collect { isAllowedToBeEdited ->
                    setBehaviourBasedOnEditStatus(
                        isOnEditMode = isAllowedToBeEdited,
                        onClicked = noteViewModel::submitNote
                    )
                }
            }
        }
    }

    private fun observerTitleAndContentInitialValue() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                noteViewModel.title.collect { title ->
                    if (title.isNotEmpty()) {
                        binding.etTitle.setText(title)
                        cancel()
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                noteViewModel.content.collect { content ->
                    if (content.isNotEmpty()) {
                        binding.tvContent.setText(content)
                        cancel()
                    }
                }
            }
        }
    }

    private fun listenerTitleAndContent() {
        binding.etTitle.addTextChangedListener {
            noteViewModel.setTitle(it.toString())
        }

        binding.tvContent.addTextChangedListener {
            noteViewModel.setContent(it.toString())
        }
    }


    @SuppressLint("ResourceAsColor")
    private fun setBehaviourBasedOnEditStatus(
        isOnEditMode: Boolean,
        onClicked: () -> Unit
    ) {
        binding.fabEditNote.apply {
            setImageResource(
                if (isOnEditMode) {
                    R.drawable.ic_check
                } else {
                    R.drawable.ic_edit_note
                }
            )

            setOnClickListener {
                if (!isOnEditMode) {
                    noteViewModel.setEditModeStatus(true)
                } else {
                    onClicked()
                    finish()
                }
            }
        }

        binding.etTitle.apply {
            isEnabled = isOnEditMode
            if (isOnEditMode) {
                setBackgroundResource(android.R.drawable.edit_text)
            } else {
                setBackgroundColor(android.R.color.transparent)
            }
        }

        binding.tvContent.isEnabled = isOnEditMode
    }


    companion object {
        private const val ID_KEY = "id"
        const val NO_ID = -1
        fun launchActivity(activity: Activity, id: Int) {
            val bundle = Bundle().apply { putInt(ID_KEY, id) }
            val intent = Intent(activity, NoteActivity::class.java)
            intent.putExtras(bundle)
            activity.startActivity(intent)
        }
    }
}
