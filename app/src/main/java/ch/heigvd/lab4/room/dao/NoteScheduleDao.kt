package ch.heigvd.lab4.room.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ch.heigvd.lab4.models.Note
import ch.heigvd.lab4.models.NoteAndSchedule
import ch.heigvd.lab4.models.Schedule

/**
 * @author Hugo DUCOMMUN, Rayane ANNEN
 */
@Dao
interface NoteScheduleDao {
    @Insert
    fun insertNote(note: Note): Long

    @Insert
    fun insertSchedule(schedule: Schedule): Long

    @Query("SELECT * FROM Note")
    fun getAll(): LiveData<List<NoteAndSchedule>>

    @Query("DELETE FROM Note")
    fun deleteAllNotes()

    @Query("DELETE FROM Schedule")
    fun deleteAllSchedules()

    @Query("SELECT COUNT(*) FROM Note")
    fun getCount(): LiveData<Int>
}