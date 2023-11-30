package ch.heigvd.lab4

import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import ch.heigvd.lab4.models.Note
import ch.heigvd.lab4.room.NoteViewModel
import ch.heigvd.lab4.room.NoteViewModelFactory
import ch.heigvd.lab4.room.SortList

/**
 * @author Hugo DUCOMMUN, Rayane ANNEN
 */
class MainActivity : AppCompatActivity() {

    private val noteViewModel : NoteViewModel by viewModels {
        NoteViewModelFactory((application as App).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onResume() {
        super.onResume()
        val prefs = getSharedPreferences("prefs", MODE_PRIVATE)
        when (prefs.getString("sort", "")) {
            "by_date" -> noteViewModel.sortNotes(SortList.BY_CREATION_DATE)
            "by_schedule" -> noteViewModel.sortNotes(SortList.BY_SCHEDULE)
            else -> noteViewModel.sortNotes(SortList.BY_CREATION_DATE)
        }
    }

    override fun onPause() {
        super.onPause()
        val prefs = getSharedPreferences("prefs", MODE_PRIVATE)
        val editor = prefs.edit()
        when (noteViewModel.sortList.value) {
            SortList.BY_CREATION_DATE -> editor.putString("sort", "by_date")
            SortList.BY_SCHEDULE -> editor.putString("sort", "by_schedule")
            else -> editor.putString("sort", "by_date")
        }
        editor.apply()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            // Handle menu item selection
            R.id.menu_btn_creation_date -> {
                noteViewModel.sortNotes(SortList.BY_CREATION_DATE)
                true
            }

            R.id.menu_btn_eta -> {
                noteViewModel.sortNotes(SortList.BY_SCHEDULE)
                true
            }

            R.id.menu_btn_generate -> {
                val note = Note.generateRandomNote()
                val schedule = Note.generateRandomSchedule()
                noteViewModel.insert(note, schedule = schedule)
                true
            }

            R.id.menu_btn_delete_all -> {
                noteViewModel.deleteAll()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}