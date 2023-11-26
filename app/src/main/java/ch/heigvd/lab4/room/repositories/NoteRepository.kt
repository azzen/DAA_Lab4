package ch.heigvd.lab4.room.repositories

import androidx.lifecycle.LiveData
import ch.heigvd.lab4.models.Note
import ch.heigvd.lab4.models.NoteAndSchedule
import ch.heigvd.lab4.models.Schedule
import ch.heigvd.lab4.room.dao.NoteScheduleDao
import kotlin.concurrent.thread

class NoteRepository(val dao: NoteScheduleDao) {


    val allNotes : LiveData<List<NoteAndSchedule>> = dao.getAll()

    val noteCount : LiveData<Int> = dao.getCount()

    fun insert(note: Note, schedule: Schedule?) {
        thread {
            val id = dao.insertNote(note)
            schedule?.let {
                schedule.ownerId = id
                dao.insertSchedule(schedule)
            }
        }
    }

    fun deleteAll() {
        thread {
            dao.deleteAllNotes()
            dao.deleteAllSchedules()
        }
    }

}