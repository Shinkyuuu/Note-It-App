package com.example.notes.views.fragments

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
        recyclerviewInit()
        return binding.root
    }

    //After pressing createNoteBtn, navigate to addNoteScreen
    private fun addNoteScreenListener() {
        binding.createNoteBtn.setOnClickListener {
            findNavController().navigate(R.id.action_mainScreen_to_addNoteScreen)
        }
    }

    //Initialize recyclerview
    private fun recyclerviewInit() {
        val adapter = RecyclerviewAdapter(this)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}