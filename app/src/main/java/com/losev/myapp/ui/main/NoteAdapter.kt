package com.losev.myapp.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.losev.myapp.R
import com.losev.myapp.domain.model.Note
import kotlinx.android.synthetic.main.item_note.view.*

class NoteAdapter(val onItemViewClick: ((note: Note) -> Unit)? = null) : RecyclerView.Adapter<NoteAdapter.NoteAdapterViewHolder>() {

    var notes: List<Note> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteAdapterViewHolder {
        return NoteAdapterViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        )
    }

    override fun getItemCount() = notes.size

    override fun onBindViewHolder(holder: NoteAdapterViewHolder, position: Int) = holder.bind(notes.get(position))


    inner class NoteAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(note: Note) = with(itemView as CardView) {
            note_title.text = note.title
            note_text.text = note.text

            val color = convertColor(note.color)

            setCardBackgroundColor(ContextCompat.getColor(this.context, color))

            this.setOnClickListener{
                onItemViewClick?.invoke(note)
            }
        }
        
        private fun convertColor(color: Note.Color): Int {
            return when(color){
                Note.Color.WHITE -> R.color.white
                Note.Color.YELLOW -> R.color.yellow
                Note.Color.GREEN -> R.color.green
                Note.Color.BLUE -> R.color.blue
                Note.Color.LIME -> R.color.lime
                Note.Color.PURPLE -> R.color.purple
                Note.Color.PINK -> R.color.pink
            }
        }
    }
}