package com.example.StudyApp.data

import android.content.Context
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthRepository(
	private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
) {

	fun getCurrentUser(): FirebaseUser? {
		return firebaseAuth.currentUser
	}

	suspend fun login(email: String, password: String): AuthResult {
		return firebaseAuth.signInWithEmailAndPassword(email, password).await()
	}

	suspend fun register(email: String, password: String): AuthResult {
		return firebaseAuth.createUserWithEmailAndPassword(email, password).await()
	}

    fun logout(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                FlashcardDatabase.clearDatabase(context)
            } catch (_: Exception) { }
        }
        firebaseAuth.signOut()
    }
}




