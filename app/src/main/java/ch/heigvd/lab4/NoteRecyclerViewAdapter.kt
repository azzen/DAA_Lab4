package ch.heigvd.lab4

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import ch.heigvd.lab4.placeholder.PlaceholderContent.PlaceholderItem
import ch.heigvd.lab4.databinding.FragmentNoteBinding

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class NoteRecyclerViewAdapter(
    private val values: List<PlaceholderItem>
) : RecyclerView.Adapter<NoteRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentNoteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item.id
        holder.contentView.text = item.content
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentNoteBinding) : RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.noteTitle
        val contentView: TextView = binding.noteText

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

}