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
        //ViewModel
        noteVM = ViewModelProvider(this).get(NoteVM::class.java)
        //ViewBinding
        _binding = FragmentAddNoteScreenBinding.inflate(inflater, container, false)
        binding.todaysDate.text = CurrentDate().currentDate()

        addNoteBtnLstnr()
        addNoteBackBtnlstnr()
        return binding.root
    }

    //After pressing addNoteBtn, navigate to add note to database
    private fun addNoteBtnLstnr() {
        binding.addNoteBtn.setOnClickListener{
            addNote()
        }
    }

    //After pressing addNoteBackBtn, navigate to mainScreen
    private fun addNoteBackBtnlstnr() {
        binding.addNoteBackBtn.setOnClickListener{
            findNavController().navigate(R.id.action_addNoteScreen_to_mainScreen)
        }
    }

    //Create a note from the editTexts and add it to database
    private fun addNote() {
        val noteTitle = binding.noteTitleEditText.text.toString()
        val noteBody = binding.noteBodyEditText.text.toString()
        val noteDate = binding.todaysDate.text.toString()
        val noteColor = "blue"

        if (checkInput(noteTitle, noteBody)) {
            val newNote = Note(0, noteTitle, noteBody, noteDate, noteColor)

            noteVM.addNote(newNote)
            findNavController().navigate(R.id.action_addNoteScreen_to_mainScreen)
            Toast.makeText(requireContext(), "Noted!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Forgetting something?", Toast.LENGTH_SHORT).show()
        }
    }

    //Check if the note contains any contents
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