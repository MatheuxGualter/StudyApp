package com.example.StudyApp.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirestoreRepository(
	private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
	private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

	private fun requireUserId(): String {
		return auth.currentUser?.uid
			?: throw IllegalStateException("Usuário não autenticado")
	}

	private fun userDoc(): DocumentReference {
		val uid = requireUserId()
		return firestore.collection("users").document(uid)
	}

	private fun decksCollection(): CollectionReference {
		return userDoc().collection("my_decks")
	}

	private fun flashcardsCollection(deckKey: String): CollectionReference {
		return decksCollection().document(deckKey).collection("flashcards")
	}

	// Decks
	suspend fun saveDeck(deck: Deck) {
		val data = hashMapOf(
			"name" to deck.name,
			"theme" to deck.theme,
			"createdAt" to deck.createdAt,
			"localId" to deck.id
		)

		if (deck.id > 0) {
			decksCollection().document(deck.id.toString()).set(data).await()
		} else {
			decksCollection().add(data).await()
		}
	}

	suspend fun deleteDeck(deck: Deck) {
		val key = if (deck.id > 0) deck.id.toString() else return
		decksCollection().document(key).delete().await()
	}

	fun getDecks(): Flow<List<Deck>> = callbackFlow {
		val registration = decksCollection()
			.orderBy("createdAt")
			.addSnapshotListener { snapshot, error ->
				if (error != null) {
					trySend(emptyList()).isSuccess
					return@addSnapshotListener
				}
				val list = snapshot?.documents?.map { doc ->
					val name = doc.getString("name") ?: ""
					val theme = doc.getString("theme") ?: ""
					val createdAt = (doc.getLong("createdAt") ?: 0L)
					val localId = (doc.getLong("localId") ?: 0L)
					Deck(
						id = localId,
						name = name,
						theme = theme,
						createdAt = createdAt
					)
				} ?: emptyList()
				trySend(list).isSuccess
			}
		awaitClose { registration.remove() }
	}

	// Flashcards (exemplo básico)
	suspend fun saveFlashcard(deckId: Long, flashcard: Flashcard) {
		val deckKey = if (deckId > 0) deckId.toString() else {
			throw IllegalArgumentException("deckId inválido para sincronização")
		}
		val data = hashMapOf(
			"deckId" to flashcard.deckId,
			"type" to flashcard.type.name,
			"front" to flashcard.front,
			"back" to flashcard.back,
			"clozeText" to flashcard.clozeText,
			"clozeAnswer" to flashcard.clozeAnswer,
			"options" to flashcard.options,
			"correctOptionIndex" to flashcard.correctOptionIndex,
			"lastReviewed" to (flashcard.lastReviewed?.time ?: 0L),
			"nextReviewDate" to (flashcard.nextReviewDate?.time ?: 0L),
			"easeFactor" to flashcard.easeFactor,
			"interval" to flashcard.interval,
			"repetitions" to flashcard.repetitions,
			"createdAt" to flashcard.createdAt,
			"localId" to flashcard.id
		)
		if (flashcard.id > 0) {
			flashcardsCollection(deckKey).document(flashcard.id.toString()).set(data).await()
		} else {
			flashcardsCollection(deckKey).add(data).await()
		}
	}

	suspend fun deleteFlashcard(deckId: Long, flashcard: Flashcard) {
		val deckKey = deckId.toString()
		if (flashcard.id <= 0) return
		flashcardsCollection(deckKey).document(flashcard.id.toString()).delete().await()
	}

	fun getFlashcards(deckId: Long): Flow<List<Flashcard>> = callbackFlow {
		val deckKey = deckId.toString()
		val registration = flashcardsCollection(deckKey)
			.orderBy("createdAt")
			.addSnapshotListener { snapshot, error ->
				if (error != null) {
					trySend(emptyList()).isSuccess
					return@addSnapshotListener
				}
				val list = snapshot?.documents?.map { doc ->
					Flashcard(
						id = (doc.getLong("localId") ?: 0L),
						deckId = deckId,
						type = FlashcardType.valueOf(doc.getString("type") ?: FlashcardType.FRONT_BACK.name),
						front = doc.getString("front") ?: "",
						back = doc.getString("back") ?: "",
						clozeText = doc.getString("clozeText"),
						clozeAnswer = doc.getString("clozeAnswer"),
						options = (doc.get("options") as? List<*>)?.mapNotNull { it as? String },
						correctOptionIndex = (doc.getLong("correctOptionIndex")?.toInt()),
						lastReviewed = doc.getLong("lastReviewed")?.takeIf { it > 0 }?.let { java.util.Date(it) },
						nextReviewDate = doc.getLong("nextReviewDate")?.takeIf { it > 0 }?.let { java.util.Date(it) },
						easeFactor = (doc.getDouble("easeFactor") ?: doc.getLong("easeFactor")?.toDouble() ?: 2.5).toFloat(),
						interval = (doc.getLong("interval") ?: 0L).toInt(),
						repetitions = (doc.getLong("repetitions") ?: 0L).toInt(),
						createdAt = (doc.getLong("createdAt") ?: 0L)
					)
				} ?: emptyList()
				trySend(list).isSuccess
			}
		awaitClose { registration.remove() }
	}
}


