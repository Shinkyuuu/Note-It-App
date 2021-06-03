package com.example.notes.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
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

        //View binding
        _binding = FragmentMainScreenBinding.inflate(inflater, container, false)

        init()
        return binding.root
    }


    private fun init() {
        addNoteScreenListener()
        recyclerviewInit()
    }

    private fun addNoteScreenListener() {
        binding.addNoteBtn.setOnClickListener {
            findNavController().navigate(R.id.action_mainScreen_to_addNoteScreen3)
        }
    }

    private fun recyclerviewInit() {
        val adapter = RecyclerviewAdapter(this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }
        binding.recyclerView.setHasFixedSize(true)

        noteVM.readAllNotes.observe(viewLifecycleOwner, { updatedNotes ->
            adapter.setNoteData(updatedNotes)
        })
    }

    private fun generateItem (max: Int) : ArrayList<Note> {
        val list = ArrayList<Note>()
        val fakeTitles = arrayListOf("This is an example of a shorter title", "Here is a slightly larger one that doesn't work", "This one is small")
        val fakeDates = arrayListOf("May 3, 2021", "September 14, 2020", "April 25, 2021")
        val colorList = arrayListOf("orange", "blue", "purple", "pink", "green")
        for (i in 0 until max) {
            val rnds = (0..2).random()
            val rnds2 = (0..4).random()
            val newNote = Note(1, fakeTitles[rnds] + i, "", fakeDates[rnds], colorList[rnds2])
            list += newNote
        }

        return list
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}