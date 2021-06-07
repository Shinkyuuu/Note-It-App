package com.example.notes.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notes.R
import com.example.notes.databinding.FragmentAddTitleScreenBinding
import com.example.notes.objects.Note

class AddTitleScreen : Fragment() {
    private var _binding: FragmentAddTitleScreenBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<AddTitleScreenArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddTitleScreenBinding.inflate(inflater, container, false)

        binding.noteTitleEditText.setText(args.newNote.title)
        addTitleBtnLstnr()
        addTitleBackBtnLstnr()
        return binding.root
    }

    private fun checkValidTitle(title: String) : Boolean = title.isNotEmpty()

    private fun addTitleBtnLstnr() {
        binding.addTitleDoneBtn.setOnClickListener {
            addTitle()
        }
    }

    private fun addTitle() {
        val noteTitle = binding.noteTitleEditText.text.toString().trim()
        val noteBody = args.newNote.body
        val noteDate = args.newNote.date
        val noteColor = args.newNote.color

        if (checkValidTitle(noteTitle)) {
            val newNote = Note(0, noteTitle, noteBody, noteDate, noteColor)
            val action = AddTitleScreenDirections.actionAddTitleScreenToAddBodyScreen(newNote)

            findNavController().navigate(action)
        } else {
            Toast.makeText(requireContext(), "Forgetting something?", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addTitleBackBtnLstnr() {
        binding.addTitleBackBtn.setOnClickListener {
            findNavController().navigate(R.id.action_addTitleScreen_to_mainScreen)
        }
    }
}