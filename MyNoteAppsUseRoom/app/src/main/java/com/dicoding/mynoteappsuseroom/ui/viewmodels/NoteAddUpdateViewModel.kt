package com.dicoding.mynoteappsuseroom.ui.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import com.dicoding.mynoteappsuseroom.database.Note
import com.dicoding.mynoteappsuseroom.repository.NoteRepository

class NoteAddUpdateViewModel(application: Application) : ViewModel() {
    private val mNoteRepository: NoteRepository = NoteRepository(application)

    fun insert(note: Note) {
        mNoteRepository.insert(note)
    }

    fun update(note: Note) {
        mNoteRepository.update(note)
    }

    fun delete(note: Note) {
        mNoteRepository.delete(note)
    }

}