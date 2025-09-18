package com.example.StudyApp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.StudyApp.data.Deck
import com.example.StudyApp.data.DeckRepository
import com.example.StudyApp.data.FirestoreRepository
import com.example.StudyApp.data.FlashcardDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class DeckViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: DeckRepository
    private val cloud: FirestoreRepository = FirestoreRepository()
    val allDecks: Flow<List<Deck>>

    init {
        val deckDao = FlashcardDatabase.getDatabase(application).deckDao()
        repository = DeckRepository(deckDao)
        allDecks = repository.allDecks
    }

    fun insert(deck: Deck) = viewModelScope.launch {
        try {
            val id = repository.insert(deck)
            println("Deck inserido com ID: $id")
            cloud.saveDeck(deck.copy(id = id))
        } catch (e: Exception) {
            println("Erro ao inserir deck: ${e.message}")
            e.printStackTrace()
            throw e
        }
    }

    fun update(deck: Deck) = viewModelScope.launch {
        repository.update(deck)
        runCatching { cloud.saveDeck(deck) }
    }

    fun delete(deck: Deck) = viewModelScope.launch {
        repository.delete(deck)
        runCatching { cloud.deleteDeck(deck) }
    }

    fun getDeckById(id: Long) = viewModelScope.launch {
        repository.getDeckById(id)
    }

    suspend fun getFlashcardCountForDeck(deckId: Long): Int {
        return repository.getFlashcardCountForDeck(deckId)
    }

    fun syncDecksFromFirestore() {
        viewModelScope.launch {
            try {
                cloud.getDecks().collect { remoteDecks ->
                    remoteDecks.forEach { deck ->
                        if (deck.name.isNotEmpty()) {
                            repository.insert(deck)
                        }
                    }
                }
            } catch (_: Exception) { }
        }
    }
} 