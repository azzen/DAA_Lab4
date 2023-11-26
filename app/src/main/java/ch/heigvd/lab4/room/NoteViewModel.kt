package ch.heigvd.lab4.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ch.heigvd.lab4.models.Note
import ch.heigvd.lab4.models.Schedule
import ch.heigvd.lab4.room.repositories.NoteRepository

class NoteViewModel(private val noteRepository: NoteRepository) : ViewModel() {
    val allNotes = noteRepository.allNotes
    val noteCount = noteRepository.noteCount

    fun insert(note: Note, schedule: Schedule?) {
        noteRepository.insert(note, schedule)
    }

    fun deleteAll() {
        noteRepository.deleteAll()
    }

    fun sortNotes() {
        // TODO: sort notes by schedule
    }

}

class NoteViewModelFactory(private val repository: NoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            return NoteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}