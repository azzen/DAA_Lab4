package ch.heigvd.lab4

import android.app.Application
import ch.heigvd.lab4.room.NoteDatabase
import ch.heigvd.lab4.room.repositories.NoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class App : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob()) // ????

    val repository by lazy {
        val db = NoteDatabase.getDatabase(this)
        NoteRepository(db.noteScheduleDao(), applicationScope)
    }
}