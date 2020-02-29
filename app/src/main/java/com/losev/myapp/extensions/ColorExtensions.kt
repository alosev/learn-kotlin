package com.losev.myapp.extensions

import android.content.Context
import androidx.core.content.ContextCompat
import com.losev.myapp.R
import com.losev.myapp.domain.model.Note


fun Note.Color.getColorRes() = when (this) {
    Note.Color.WHITE -> R.color.white
    Note.Color.YELLOW -> R.color.yellow
    Note.Color.GREEN -> R.color.green
    Note.Color.BLUE -> R.color.blue
    Note.Color.LIME -> R.color.lime
    Note.Color.PURPLE -> R.color.purple
    Note.Color.PINK -> R.color.pink
}

fun Note.Color.getColor(context: Context) = ContextCompat.getColor(context, this.getColorRes())
