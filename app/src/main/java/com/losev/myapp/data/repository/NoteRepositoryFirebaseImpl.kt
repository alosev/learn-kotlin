package com.losev.myapp.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.losev.myapp.domain.model.NoAuthException
import com.losev.myapp.domain.model.Note
import com.losev.myapp.domain.model.NoteResult
import com.losev.myapp.domain.model.User
import com.losev.myapp.domain.repository.NoteRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class NoteRepositoryFirebaseImpl(private val fireStore: FirebaseFirestore, private val firebaseAuth: FirebaseAuth) : NoteRepository {

    companion object {
        private const val NOTES_COLLECTION = "notes"
        private const val USER_COLLECTION = "users"
    }

    private val currentUser
        get() = firebaseAuth.currentUser

    private val userNotesCollection
        get() = currentUser?.let { user ->
            fireStore.collection(USER_COLLECTION).document(user.uid).collection(NOTES_COLLECTION)
        } ?: throw NoAuthException()

    override suspend fun getUser(): User? = suspendCoroutine { continuation ->
        val user = currentUser?.let { user ->
            User(user.displayName.orEmpty(), user.email.orEmpty())
        }

        continuation.resume(user)
    }

    override fun getNotes(): ReceiveChannel<NoteResult> = Channel<NoteResult>(Channel.CONFLATED).apply {
        var registration: ListenerRegistration? = null;

        try {
            registration = userNotesCollection.addSnapshotListener { snapshot, e ->
                val value = e?.let { error ->
                    throw error
                } ?: let {
                    snapshot?.let { items ->
                        NoteResult.Success(items.map { it.toObject(Note::class.java) })
                    }
                }

                value?.let { offer(it) }
            }
        } catch (e: Throwable) {
            offer(NoteResult.Error(e))
        }

        invokeOnClose {
            registration?.remove()
        }
    }

    override suspend fun getNote(noteId: String): Note = suspendCoroutine { continuation ->
        try {
            userNotesCollection.document(noteId).get()
                    .addOnSuccessListener { snapshot ->
                        continuation.resume(snapshot.toObject(Note::class.java)!!)
                    }
                    .addOnFailureListener { error ->
                        throw error
                    }
        } catch (e: Throwable) {
            continuation.resumeWithException(e)
        }
    }

    override suspend fun saveNote(note: Note): Unit = suspendCoroutine { continuation ->
        try {
            userNotesCollection.document(note.id).set(note)
                    .addOnSuccessListener {
                        continuation.resume(Unit)
                    }
                    .addOnFailureListener { error ->
                        throw error
                    }
        } catch (e: Throwable) {
            continuation.resumeWithException(e)
        }
    }

    override suspend fun deleteNote(noteId: String): Unit = suspendCoroutine { continuation ->
        try {
            userNotesCollection.document(noteId).delete()
                    .addOnSuccessListener {
                        continuation.resume(Unit)
                    }
                    .addOnFailureListener { error ->
                        throw error
                    }

        } catch (e: Throwable) {
            continuation.resumeWithException(e)
        }
    }
}