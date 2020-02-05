package com.losev.myapp.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.losev.myapp.R
import com.losev.myapp.domain.model.Note
import kotlinx.android.synthetic.main.item_note.view.*

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteAdapterViewHolder>(){

    public var notes: List<Note> = listOf()
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

    class NoteAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(note: Note) = with(itemView as CardView){
            note_title.text = note.title
            note_text.text = note.text
            setCardBackgroundColor(note.color)
        }
    }
}