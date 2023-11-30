package ch.heigvd.lab4.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ch.heigvd.lab4.models.Note
import ch.heigvd.lab4.models.NoteAndSchedule
import ch.heigvd.lab4.models.Schedule
import ch.heigvd.lab4.room.repositories.NoteRepository

enum class SortList {
    BY_SCHEDULE,
    BY_CREATION_DATE
}

class NoteViewModel(private val noteRepository: NoteRepository) : ViewModel() {
    private val _allNotes = noteRepository.allNotes
    val allNotes : LiveData<List<NoteAndSchedule>> get() = _allNotes
    val noteCount = noteRepository.noteCount
    private val _sortList = MutableLiveData(SortList.BY_SCHEDULE)
    val sortList : LiveData<SortList> get() = _sortList

    fun insert(note: Note, schedule: Schedule?) {
        noteRepository.insert(note, schedule)
    }

    fun deleteAll() {
        noteRepository.deleteAll()
    }

    fun sortNotes(sort: SortList) {
        when(sort) {
            SortList.BY_CREATION_DATE -> {
                _sortList.postValue(sort)
            }
            SortList.BY_SCHEDULE -> {
                _sortList.postValue(sort)
            }
        }
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