package com.example.notes.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.notes.objects.Note
import com.example.notes.repositories.NoteDatabase
import com.example.notes.repositories.NoteRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteVM(app: Application) : AndroidViewModel(app) {
    private val readAllNotes: LiveData<List<Note>>
    private val repo: NoteRepo

    init {
        val noteDao = NoteDatabase.getDatabase(app).noteDao()
        repo = NoteRepo(noteDao)
        readAllNotes = repo.readAllNotes
    }

    fun addNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) { 
            repo.addNote(note)
        }
    }
}