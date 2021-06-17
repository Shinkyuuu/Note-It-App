package com.example.notes.views.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notes.R
import com.example.notes.adapters.RecyclerviewAdapter
import com.example.notes.databinding.FragmentMainScreenBinding
import com.example.notes.objects.Note
import com.example.notes.viewmodels.NoteVM

class MainScreen : Fragment() {
    private var adapter = RecyclerviewAdapter(this)
    private lateinit var noteVM: NoteVM
    private var _binding : FragmentMainScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //ViewModel
        noteVM = ViewModelProvider(this).get(NoteVM::class.java)
        //ViewBinding
        _binding = FragmentMainScreenBinding.inflate(inflater, container, false)

        recyclerViewTransitionFix()
        addNoteScreenListener()
        deleteNotesListener()
        recyclerviewInit()

        return binding.root
    }

    //Initialize recyclerview
    private fun recyclerviewInit() {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }
        binding.recyclerView.setHasFixedSize(true)

        //Observe updates to the recyclerview
        noteVM.readAllNotes.observe(viewLifecycleOwner, { updatedNotes ->
            adapter.setNoteData(updatedNotes)
        })
    }

    //Fixes recyclerView animation not working after selecting a note within it.
    @SuppressLint("ClickableViewAccessibility")
    private fun recyclerViewTransitionFix() {
        binding.recyclerView.setOnTouchListener { _, event ->
            binding.motionScene.onTouchEvent(event)
            return@setOnTouchListener false
        }
    }

    //After pressing createNoteBtn, navigate to addNoteScreen
    private fun addNoteScreenListener() {
        binding.createNoteBtn.setOnClickListener {
            val action = MainScreenDirections.actionMainScreenToAddTitleScreen(Note(0))
            findNavController().navigate(action)
        }
    }

    private fun deleteNotesListener() {
        binding.deleteNoteBtn.setOnClickListener {
            val toDeleteNotes = adapter.receiveToDeleteNotes()
            noteVM.deleteNotes(toDeleteNotes)
            adapter.unselectAll()
            turnOffSelectMode()
        }
    }

    fun turnOnSelectMode() {
        binding.motionScene.transitionToState(R.id.end)
    }

    fun turnOffSelectMode() {
        binding.motionScene.transitionToStart()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}