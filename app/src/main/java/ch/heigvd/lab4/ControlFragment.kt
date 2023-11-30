package ch.heigvd.lab4

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import ch.heigvd.lab4.models.Note
import ch.heigvd.lab4.room.NoteViewModel
import ch.heigvd.lab4.room.NoteViewModelFactory

/**
 * Control panel fragment (instead of the menu in the landscape mode)
 * author: Hugo DUCOMMUN, Rayane ANNEN
 */
class ControlFragment : Fragment() {
    private val noteViewModel : NoteViewModel by activityViewModels {
        NoteViewModelFactory((requireActivity().application as App).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_control, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // bind views
        val generate = view.findViewById<Button>(R.id.btn_generate_notes)
        val delete = view.findViewById<Button>(R.id.btn_delete_notes)
        val counter = view.findViewById<TextView>(R.id.count_notes)

        // update counter when the number of notes changes

        noteViewModel.noteCount.observe(viewLifecycleOwner) {
            counter.text = it.toString()
        }

        // setup listeners

        generate.setOnClickListener {
            val note = Note.generateRandomNote()
            val schedule = Note.generateRandomSchedule()
            noteViewModel.insert(note, schedule = schedule)
        }

        delete.setOnClickListener {
            noteViewModel.deleteAll()
        }
    }
}