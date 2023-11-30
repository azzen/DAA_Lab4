package ch.heigvd.lab4.room.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ch.heigvd.lab4.models.Note
import ch.heigvd.lab4.models.NoteAndSchedule
import ch.heigvd.lab4.models.Schedule
import ch.heigvd.lab4.room.dao.NoteScheduleDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

/**
 * Repository for Note and Schedule
 * @author Hugo DUCOMMUN, Rayane ANNEN
 */
class NoteRepository(val dao: NoteScheduleDao, val scope: CoroutineScope) {
    val allNotes : LiveData<List<NoteAndSchedule>> = dao.getAll()

    val noteCount : LiveData<Int> = dao.getCount()

    // dispatches insert to IO thread
    fun insert(note: Note, schedule: Schedule?) {
        scope.launch(Dispatchers.IO) {
            val id = dao.insertNote(note)
            schedule?.let {
                schedule.ownerId = id
                dao.insertSchedule(schedule)
            }
        }
    }

    // dispatches deleteAll to IO thread
    fun deleteAll() {
        scope.launch(Dispatchers.IO) {
            dao.deleteAllNotes()
            dao.deleteAllSchedules()
        }
    }

}