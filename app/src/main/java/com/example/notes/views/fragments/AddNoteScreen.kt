package com.example.notes.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.notes.R
import com.example.notes.databinding.FragmentAddNoteScreenBinding
import com.example.notes.objects.CurrentDate
import com.example.notes.objects.Note
import com.example.notes.viewmodels.NoteVM

class AddNoteScreen : Fragment() {
    private lateinit var noteVM : NoteVM
    private var _binding : FragmentAddNoteScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddNoteScreenBinding.inflate(inflater, container, false)
        noteVM = ViewModelProvider(this).get(NoteVM::class.java)

        init()

        return binding.root
    }

    private fun init() {
        createNoteBtnLstnr()
        addNoteBackBtnlstnr()

        binding.todaysDate.text = CurrentDate().currentDate()
    }

    private fun createNoteBtnLstnr() {
        binding.createNoteBtn.setOnClickListener{
            createNote()
        }
    }

    private fun addNoteBackBtnlstnr() {
        binding.addNoteBackBtn.setOnClickListener{
            findNavController().navigate(R.id.action_addNoteScreen_to_mainScreen)
        }
    }

    private fun createNote() {
        val noteTitle = binding.noteTitleEditText.text.toString()
        val noteBody = binding.noteBodyEditText.text.toString()
        val noteDate = binding.todaysDate.text.toString()
        val noteColor = "blue"
         val newNote = Note(0, noteTitle, noteBody, noteDate, noteColor)

        if (checkInput(noteTitle, noteBody)) {
            noteVM.addNote(newNote)
            Toast.makeText(requireContext(), "Noted!", Toast.LENGTH_SHORT).show()

            findNavController().navigate(R.id.action_addNoteScreen_to_mainScreen)
        } else {
            Toast.makeText(requireContext(), "Forgetting something?", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkInput(title: String, body: String) : Boolean {
        if (title.isEmpty() && body.isEmpty()) {
            return false
        }

        return true
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}