package com.example.notes.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notes.R
import com.example.notes.databinding.FragmentUpdateNoteScreenBinding
import com.example.notes.objects.CurrentDate
import com.example.notes.objects.Note
import com.example.notes.viewmodels.NoteVM

class UpdateNoteScreen : Fragment() {
    private lateinit var noteVM: NoteVM
    private val args by navArgs<UpdateNoteScreenArgs>()
    private var _binding : FragmentUpdateNoteScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //ViewModel
        noteVM = ViewModelProvider(this).get(NoteVM::class.java)
        //ViewBinding
        _binding = FragmentUpdateNoteScreenBinding.inflate(inflater, container, false)

        setTexts()
        updateNoteBtnLstnr()
        updateNoteBackBtnLstnr()
        return binding.root
    }

    //Add the note's title and body to the editTexts
    private fun setTexts() {
        binding.noteTitleEditTextU.setText(args.currentNote.title)
        binding.noteBodyEditTextU.setText(args.currentNote.body)
        binding.todaysDateU.text = CurrentDate().currentDate()
    }

    //After pressing updateNoteBtn, update note in database
    private fun updateNoteBtnLstnr() {
        binding.updateNoteBtn.setOnClickListener { updateNote() }
    }

    //After pressing updateNoteBackBtn, navigate to mainScreen
    private fun updateNoteBackBtnLstnr() {
        binding.updateNoteBackBtn.setOnClickListener {
            findNavController().navigate(R.id.action_updateNoteScreen_to_mainScreen)
        }
    }

    //Create updated note and update the database
    private fun updateNote() {
        val noteTitle = binding.noteTitleEditTextU.text.toString()
        val noteBody = binding.noteBodyEditTextU.text.toString()
        val noteDate = binding.todaysDateU.text.toString()
        val noteId = args.currentNote.id

        if (checkInput(noteTitle, noteBody)) {
            val updatedNote = Note(noteId, noteTitle, noteBody, noteDate)

            noteVM.updateNote(updatedNote)
            findNavController().navigate(R.id.action_updateNoteScreen_to_mainScreen)
            Toast.makeText(requireContext(), "Noted!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Forgetting something?", Toast.LENGTH_SHORT).show()
        }
    }

    //Check if note contains any contents
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