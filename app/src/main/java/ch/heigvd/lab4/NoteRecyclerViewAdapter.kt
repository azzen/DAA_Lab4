package ch.heigvd.lab4

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import ch.heigvd.lab4.databinding.FragmentNoteBinding
import ch.heigvd.lab4.models.NoteAndSchedule
import ch.heigvd.lab4.models.State
import ch.heigvd.lab4.models.Type
import ch.heigvd.lab4.room.SortList
import java.text.SimpleDateFormat

/**
 * @author: Hugo DUCOMMUN, Rayane ANNEN
 */
class NoteRecyclerViewAdapter(
    private val values: List<NoteAndSchedule>?
) : RecyclerView.Adapter<NoteRecyclerViewAdapter.ViewHolder>() {

    private val allNotes = ArrayList<NoteAndSchedule>()
    var items = listOf<NoteAndSchedule>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentNoteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }
    // Gère l'affichage des notes en fonction de leurs propriétés
    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        val notes = items[position]
        holder.tvTitle.text = notes.note.title
        holder.tvDesc.text = notes.note.text

        // note type
        when(notes.note.type) {
            Type.NONE -> holder.ivIcon.setImageResource(R.drawable.note)
            Type.TODO -> holder.ivIcon.setImageResource(R.drawable.todo)
            Type.SHOPPING -> holder.ivIcon.setImageResource(R.drawable.shopping)
            Type.WORK -> holder.ivIcon.setImageResource(R.drawable.work)
            Type.FAMILY -> holder.ivIcon.setImageResource(R.drawable.family)
        }

        // state (done or in progress)
        when (notes.note.state) {
            State.IN_PROGRESS -> holder.ivIcon.setColorFilter(Color.BLACK)
            State.DONE -> holder.ivIcon.setColorFilter(Color.GREEN)
        }

        // show clock if schedule is not null
        if (notes.schedule != null) {
            // date as string and set it to tvDate
            val dueTime = SimpleDateFormat("dd/MM/yyyy").format(notes.schedule.date.time)
            val nowTime = SimpleDateFormat("dd/MM/yyyy").format(System.currentTimeMillis())
            val dayFormat = SimpleDateFormat("dd/MM/yyyy")

            val due = dayFormat.parse(dueTime)
            val now = dayFormat.parse(nowTime)
            holder.ivClock.visibility = View.VISIBLE
            holder.tvDate.visibility = View.VISIBLE
            holder.ivClock.setImageResource(R.drawable.clock)

            if (due.before(now)) { // late > at least a day before
                holder.ivClock.setColorFilter(Color.RED)
                holder.tvDate.text = context.getString(R.string.late)
            } else if (due == now) { // same day
                holder.ivClock.setColorFilter(Color.YELLOW)
                holder.tvDate.text = context.getString(R.string.today)
            } else { // due > at least a day after
                holder.ivClock.setColorFilter(Color.BLACK)
                val dt = due.time - now.time
                val seconds = dt / 1000;
                val minutes = seconds / 60;
                val hours = minutes / 60;
                val days = hours / 24;
                val weeks = days / 7;
                val months = days / 30;
                if (months > 1) {
                    holder.tvDate.text = context.getString(R.string.months, months)
                } else if (days < 7) {
                    holder.tvDate.text = context.getString(R.string.days, days)
                } else if (weeks > 0) {
                    holder.tvDate.text = context.getString(R.string.weeks, weeks)
                }
            }
        } else {
            holder.ivClock.visibility = View.INVISIBLE
            holder.tvDate.visibility = View.INVISIBLE
        }
    }

    override fun getItemCount(): Int = items.size

    // Sort notes by creation date or schedule date
    // update items accordingly
    fun sortNotes(sort: SortList) {
        when(sort) {
            SortList.BY_CREATION_DATE -> {
                items = items.sortedBy { it.note.creationDate }
            }
            SortList.BY_SCHEDULE -> {
                items = items.sortedWith(compareBy(nullsLast()) { it.schedule?.date })
            }
        }
    }

    // Update the list of notes
    fun updateList(list: List<NoteAndSchedule>) {
        // clear
        allNotes.clear()
        items = allNotes

        // add new list
        allNotes.addAll(list)
        items = allNotes

        // notify
        notifyDataSetChanged()
    }

    // view binding
    inner class ViewHolder(binding: FragmentNoteBinding) : RecyclerView.ViewHolder(binding.root) {
        val ivClock : ImageView = binding.noteClock
        val ivIcon : ImageView = binding.noteIcon
        val tvTitle: TextView = binding.noteTitle
        val tvDesc : TextView = binding.noteText
        val tvDate : TextView = binding.noteDate
    }

}