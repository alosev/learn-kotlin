package com.losev.myapp.data.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.losev.myapp.domain.model.NoAuthException
import com.losev.myapp.domain.model.Note
import com.losev.myapp.domain.model.NoteResult
import com.losev.myapp.domain.model.User
import com.losev.myapp.domain.repository.NoteRepository

class NoteRepositoryFirebaseImpl() : NoteRepository {

    companion object {
        private const val NOTES_COLLECTION = "notes"
        private const val USER_COLLECTION = "users"
    }

    private val fireStore by lazy {
        FirebaseFirestore.getInstance()
    }

    private val currentUser
        get() = FirebaseAuth.getInstance().currentUser

    private val userNotesCollection
        get() = currentUser?.let { user ->
            fireStore.collection(USER_COLLECTION).document(user.uid).collection(NOTES_COLLECTION)
        } ?: throw NoAuthException()

    override fun getUser() = MutableLiveData<User?>().apply {
        value = currentUser?.let { user ->
            User(user.displayName.orEmpty(), user.email.orEmpty())
        }
    }

    override fun getNotes() = MutableLiveData<NoteResult>().apply {
        try {
            userNotesCollection.addSnapshotListener { snapshot, e ->
                e?.let { error ->
                    throw error
                } ?: let {
                    snapshot?.let { items ->
                        value = NoteResult.Success(items.map { it.toObject(Note::class.java) })
                    }
                }
            }
        } catch (e: Throwable) {
            value = NoteResult.Error(e)
        }
    }

    override fun getNote(id: String) = MutableLiveData<NoteResult>().apply {
        try {
            userNotesCollection.document(id).get()
                    .addOnSuccessListener { snapshot ->
                        value = NoteResult.Success(snapshot.toObject(Note::class.java))
                    }
                    .addOnFailureListener { error ->
                        throw error
                    }
        } catch (e: Throwable) {
            value = NoteResult.Error(e)
        }
    }

    override fun saveNote(note: Note) = MutableLiveData<NoteResult>().apply {
        try {
            userNotesCollection.document(note.id).set(note)
                    .addOnSuccessListener {
                        value = NoteResult.Success(note)
                    }
                    .addOnFailureListener { error ->
                        throw error
                    }
        } catch (e: Throwable) {
            value = NoteResult.Error(e)
        }
    }

}