package ch.heigvd.lab4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_btn_creation_date -> {
                true
            }

            R.id.menu_btn_eta -> {
                true
            }

            R.id.menu_btn_generate -> {
                true
            }

            R.id.menu_btn_delete_all -> {
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}