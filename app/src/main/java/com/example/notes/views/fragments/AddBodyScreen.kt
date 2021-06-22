package com.example.notes.views.fragments

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notes.R
import com.example.notes.databinding.FragmentAddBodyScreenBinding
import com.example.notes.objects.CurrentDate
import com.example.notes.objects.Note
import com.example.notes.viewmodels.NoteVM

class AddBodyScreen : Fragment() {
    private lateinit var noteVM : NoteVM
    private var _binding : FragmentAddBodyScreenBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<AddBodyScreenArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Shared Transition Animation
        val animation = TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation

        //ViewModel
        noteVM = ViewModelProvider(this).get(NoteVM::class.java)
        //ViewBinding
        _binding = FragmentAddBodyScreenBinding.inflate(inflater, container, false)
        binding.noteBodyEditText.setText(args.newNote.body)
        binding.todaysDate.text = CurrentDate().currentDate()

        addNoteBtnLstnr()
        addNoteBackBtnLstnr()

        return binding.root
    }

    //After pressing addNoteBtn, navigate to add note to database
    private fun addNoteBtnLstnr() {
        binding.addNoteBtn.setOnClickListener{
            addNote()
        }
    }

    //After pressing addNoteBackBtn, navigate to addTitleScreen
    private fun addNoteBackBtnLstnr() {
        binding.addNoteBackBtn.setOnClickListener{
            val newNote = Note(args.newNote.id, args.newNote.title, binding.noteBodyEditText.text.toString().trim())
            val action = AddBodyScreenDirections.actionAddBodyScreenToAddTitleScreen(newNote)
            findNavController().navigate(action)
        }
    }

    //Create a note from the editTexts and add it to database
    private fun addNote() {
        val noteTitle = args.newNote.title
        val noteBody = binding.noteBodyEditText.text.toString().trim()
        val noteDate = binding.todaysDate.text.toString()

        if (checkInput(noteTitle, noteBody)) {
            val newNote = Note(args.newNote.id, noteTitle, noteBody, noteDate)
            noteVM.addNote(newNote)
            findNavController().navigate(R.id.action_addBodyScreen_to_mainScreen)
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