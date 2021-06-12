package com.example.notes.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notes.adapters.RecyclerviewAdapter
import com.example.notes.databinding.FragmentMainScreenBinding
import com.example.notes.objects.Note
import com.example.notes.viewmodels.NoteVM

class MainScreen : Fragment(), RecyclerviewAdapter.AdapterInterface {
    var adapter = RecyclerviewAdapter(this)
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

        addNoteScreenListener()
        turnOnSelectMode()
        recyclerviewInit()
        return binding.root
    }

    //After pressing createNoteBtn, navigate to addNoteScreen
    private fun addNoteScreenListener() {
        binding.createNoteBtn.setOnClickListener {
            val action = MainScreenDirections.actionMainScreenToAddTitleScreen(Note(0))
            findNavController().navigate(action)
        }
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

    private fun deleteNotesListener() {
        adapter.deleteNotes()
    }

    override fun turnOnSelectMode() {
        binding.mainTitle.setOnLongClickListener {
            binding.motionScene.transitionToEnd()
            return@setOnLongClickListener true
        }
    }

    private fun turnOffSelectMode() {
        adapter.unselectAll()
        TODO("Hide the delete button and cancel selection button")

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

//This is a test