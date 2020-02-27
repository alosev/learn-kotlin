package com.losev.myapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.losev.myapp.domain.model.Note
import com.losev.myapp.domain.model.NoteResult
import com.losev.myapp.domain.repository.NoteRepository

class NoteRepositoryFirebaseImpl() : NoteRepository {

    companion object {
        private const val NOTES_COLLECTION = "notes"
    }

    private val noteCollection: CollectionReference by lazy {
        FirebaseFirestore.getInstance().collection(NOTES_COLLECTION)
    }

    override fun getNotes(): LiveData<NoteResult> {
        val result = MutableLiveData<NoteResult>()

        noteCollection.addSnapshotListener { snapshot, e ->
            e?.let {
                result.value = NoteResult.Error(e)
            } ?: let {
                snapshot?.let {
                    val notes = mutableListOf<Note>()

                    for (doc in it) {
                        notes.add(doc.toObject(Note::class.java))
                    }

                    result.value = NoteResult.Success(notes)
                }
            }
        }

        return result
    }

    override fun getNote(id: String): LiveData<NoteResult> {
        val result = MutableLiveData<NoteResult>()

        noteCollection.document(id).get()
                .addOnSuccessListener { snapshot ->
                    result.value = NoteResult.Success(snapshot.toObject(Note::class.java))
                }
                .addOnFailureListener { error ->
                    result.value = NoteResult.Error(error)
                }

        return result
    }

    override fun saveNote(note: Note): LiveData<NoteResult> {
        val result = MutableLiveData<NoteResult>()

        noteCollection.document(note.id).set(note)
                .addOnSuccessListener {
                    result.value = NoteResult.Success(note)
                }
                .addOnFailureListener { error ->
                    result.value = NoteResult.Error(error)
                }

        return result;
    }


}