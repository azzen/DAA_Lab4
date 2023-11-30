package ch.heigvd.lab4

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import ch.heigvd.lab4.room.NoteViewModel
import ch.heigvd.lab4.room.NoteViewModelFactory

/**
 * A fragment representing a list of notes.
 * @author Hugo DUCOMMUN, Rayane ANNEN
 */
class NoteFragment : Fragment() {

    private val noteViewModel : NoteViewModel by activityViewModels {
        NoteViewModelFactory((requireActivity().application as App).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_note_list, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // setup recycler view
        val recycler = view.findViewById<RecyclerView>(R.id.list)
        val adapter = NoteRecyclerViewAdapter(noteViewModel.allNotes.value)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(context)
        // observes changes in the list of notes
        noteViewModel.sortList.observe(viewLifecycleOwner) {
            adapter.sortNotes(it)
        }

        noteViewModel.allNotes.observe(viewLifecycleOwner) {
            adapter.updateList(it)
        }
    }
}