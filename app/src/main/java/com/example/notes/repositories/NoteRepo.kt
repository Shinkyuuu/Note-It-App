package com.example.notes.repositories

import androidx.lifecycle.LiveData
import com.example.notes.objects.Note

class NoteRepo(private val noteDao: NoteDao) {
    val readAllNotes: LiveData<List<Note>> = noteDao.readAllNotes()

    suspend fun addNote(note: Note) {
        noteDao.addNote(note)
    }

    suspend fun updateNote(note: Note) {
        noteDao.updateNote(note)
    }

    suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note)
    }
}