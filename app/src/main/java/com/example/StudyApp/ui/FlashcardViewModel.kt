package com.example.StudyApp.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.StudyApp.data.Flashcard
import com.example.StudyApp.data.FlashcardDatabase
import com.example.StudyApp.data.FlashcardRepository
import com.example.StudyApp.data.FirestoreRepository
import com.example.StudyApp.data.UserLocation
import com.example.StudyApp.data.UserLocationDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import com.example.StudyApp.data.AIRepository
import com.example.StudyApp.data.StudySessionDao
import com.example.StudyApp.data.StudySessionEvent
import kotlinx.coroutines.launch
import java.util.Date

class FlashcardViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: FlashcardRepository
    private val userLocationDao: UserLocationDao
    private val studySessionDao: StudySessionDao
    private val aiRepository = AIRepository()
    private val cloud: FirestoreRepository = FirestoreRepository()

    // Fluxos para diferentes modos de visualização
    val allFlashcardsByReview: Flow<List<Flashcard>>
    val allFlashcardsByCreation: Flow<List<Flashcard>>
    val dueFlashcards: Flow<List<Flashcard>>
    val latestUserLocation: Flow<UserLocation?>

    private val _verificationStatus = MutableStateFlow<Boolean?>(null)
    val verificationStatus: StateFlow<Boolean?> = _verificationStatus

    init {
        val database = FlashcardDatabase.getDatabase(application)
        val flashcardDao = database.flashcardDao()
        userLocationDao = database.userLocationDao()
        studySessionDao = database.studySessionDao()
        repository = FlashcardRepository(flashcardDao)
        allFlashcardsByReview = repository.allFlashcardsByReview
        allFlashcardsByCreation = repository.allFlashcardsByCreation
        dueFlashcards = repository.getDueFlashcards()
        latestUserLocation = userLocationDao.getLatestLocation()
    }

    fun getFlashcardsForDeckByReview(deckId: Long): Flow<List<Flashcard>> {
        return repository.getFlashcardsForDeckByReview(deckId)
    }

    fun getFlashcardsForDeckByCreation(deckId: Long): Flow<List<Flashcard>> {
        return repository.getFlashcardsForDeckByCreation(deckId)
    }

    fun getDueFlashcardsForDeck(deckId: Long): Flow<List<Flashcard>> {
        return repository.getDueFlashcardsForDeck(deckId)
    }

    fun insert(flashcard: Flashcard) = viewModelScope.launch {
        val newId = repository.insert(flashcard)
        runCatching { cloud.saveFlashcard(flashcard.deckId, flashcard.copy(id = newId)) }
    }

    fun update(flashcard: Flashcard) = viewModelScope.launch {
        repository.update(flashcard)
        runCatching { cloud.saveFlashcard(flashcard.deckId, flashcard) }
    }

    fun delete(flashcard: Flashcard) = viewModelScope.launch {
        repository.delete(flashcard)
        runCatching { cloud.deleteFlashcard(flashcard.deckId, flashcard) }
    }

    suspend fun getFlashcardById(id: Long): Flashcard? {
        return repository.getById(id)
    }

    fun deleteAllFlashcardsForDeck(deckId: Long) = viewModelScope.launch {
        repository.deleteAllForDeck(deckId)
    }

    // Inicia sincronização contínua dos flashcards do deck com a nuvem
    fun startSyncForDeck(deckId: Long) {
        viewModelScope.launch {
            try {
                cloud.getFlashcards(deckId).collect { remoteCards ->
                    // Estratégia simples: inserir/atualizar localmente
                    remoteCards.forEach { card ->
                        repository.insert(card)
                    }
                }
            } catch (_: Exception) { }
        }
    }

    fun syncFlashcardsForDeck(deckId: Long) {
        viewModelScope.launch {
            try {
                cloud.getFlashcards(deckId).collect { remoteCards ->
                    remoteCards.forEach { card ->
                        repository.insert(card)
                    }
                }
            } catch (_: Exception) { }
        }
    }

    fun generateFlashcardsFromText(text: String, deckId: Long) {
        viewModelScope.launch {
            try {
                Log.d("AI_ASSISTANT", "ViewModel: Iniciando a geração de flashcards.")
                val generated = aiRepository.generateFlashcardsFromText(text, deckId)
                Log.d("AI_ASSISTANT", "ViewModel: ${generated.size} flashcards gerados com sucesso!")
                generated.forEach { insert(it) }
            } catch (e: Exception) {
                // AQUI ESTÁ A MUDANÇA IMPORTANTE!
                // Vamos registrar o erro no Logcat para ver o que está acontecendo.
                Log.e("AI_ASSISTANT", "ViewModel: Erro ao gerar flashcards.", e)
            }
        }
    }

    fun verifyAnswerWithAI(flashcard: Flashcard, userAnswer: String) {
        viewModelScope.launch {
            val question = flashcard.front
            val correct = when (flashcard.type) {
                com.example.StudyApp.data.FlashcardType.FRONT_BACK -> flashcard.back
                com.example.StudyApp.data.FlashcardType.CLOZE -> flashcard.clozeAnswer ?: ""
                com.example.StudyApp.data.FlashcardType.TEXT_INPUT -> flashcard.back
                com.example.StudyApp.data.FlashcardType.MULTIPLE_CHOICE -> {
                    val idx = flashcard.correctOptionIndex ?: -1
                    if (idx >= 0) flashcard.options?.getOrNull(idx) ?: "" else ""
                }
            }

            val result = try {
                aiRepository.verifyAnswer(question = question, correctAnswer = correct, userAnswer = userAnswer)
            } catch (_: Exception) { false }
            _verificationStatus.value = result


        }
    }
    fun resetVerificationStatus() {
        _verificationStatus.value = null
    }

    fun logStudyEvent(deckId: Long, flashcardId: Long, responseTime: Long, isCorrect: Boolean) {
        viewModelScope.launch {
            val latest = try { latestUserLocation.first() } catch (_: Exception) { null }
            val event = StudySessionEvent(
                timestamp = System.currentTimeMillis(),
                deckId = deckId,
                flashcardId = flashcardId,
                responseTimeMillis = responseTime,
                isCorrect = isCorrect,
                latitude = latest?.latitude,
                longitude = latest?.longitude
            )
            try {
                studySessionDao.insert(event)
            } catch (e: Exception) {
                Log.e("Analytics", "Falha ao salvar evento de estudo", e)
            }
        }
    }

    // Métodos para Analytics
    fun getAllStudyEvents(): Flow<List<StudySessionEvent>> {
        return studySessionDao.getAllEvents()
    }

    fun getStudyEventsForDeck(deckId: Long): Flow<List<StudySessionEvent>> {
        return studySessionDao.getEventsForDeck(deckId)
    }

    suspend fun getTotalCorrectAnswers(): Int {
        return studySessionDao.getTotalCorrectAnswers()
    }

    suspend fun getTotalWrongAnswers(): Int {
        return studySessionDao.getTotalWrongAnswers()
    }

    suspend fun getTotalAnswers(): Int {
        return studySessionDao.getTotalAnswers()
    }

    suspend fun getAverageResponseTimeForCorrect(): Double? {
        return studySessionDao.getAverageResponseTimeForCorrect()
    }

    suspend fun getTotalDecksStudied(): Int {
        return studySessionDao.getTotalDecksStudied()
    }

    suspend fun getTotalFlashcardsStudied(): Int {
        return studySessionDao.getTotalFlashcardsStudied()
    }

    fun calculateNextReview(flashcard: Flashcard, quality: Int): Flashcard {
        val now = Date()
        val newEaseFactor = calculateNewEaseFactor(flashcard.easeFactor, quality)
        val newInterval = calculateNewInterval(flashcard.interval, newEaseFactor, quality)
        val nextReview = Date(now.time + (newInterval * 24 * 60 * 60 * 1000L))

        return flashcard.copy(
            lastReviewed = now,
            nextReviewDate = nextReview,
            easeFactor = newEaseFactor,
            interval = newInterval,
            repetitions = flashcard.repetitions + 1
        )
    }

    // Métodos para gerenciar localização do usuário
    fun saveUserLocation(name: String, iconName: String, latitude: Double, longitude: Double) = viewModelScope.launch {
        val userLocation = UserLocation(
            name = name,
            iconName = iconName,
            latitude = latitude,
            longitude = longitude
        )
        userLocationDao.insert(userLocation)
    }

    // Método sobrecarregado para salvar localização do usuário com apenas latitude e longitude
    fun saveUserLocation(latitude: Double, longitude: Double) = viewModelScope.launch {
        val userLocation = UserLocation(
            name = "Localização automática",
            iconName = "ic_location",
            latitude = latitude,
            longitude = longitude
        )
        userLocationDao.insert(userLocation)
    }
    
    fun getAllUserLocations() = userLocationDao.getAllUserLocations()
    
    fun deleteUserLocation(id: Long) = viewModelScope.launch {
        userLocationDao.deleteById(id)
    }

    fun clearUserLocationHistory() = viewModelScope.launch {
        userLocationDao.deleteAll()
    }

    private fun calculateNewEaseFactor(oldEaseFactor: Float, quality: Int): Float {
        val newEaseFactor = oldEaseFactor + (0.1f - (5 - quality) * (0.08f + (5 - quality) * 0.02f))
        return maxOf(1.3f, newEaseFactor)
    }

    private fun calculateNewInterval(oldInterval: Int, easeFactor: Float, quality: Int): Int {
        return when {
            quality < 3 -> 1
            oldInterval == 0 -> 1
            oldInterval == 1 -> 6
            else -> (oldInterval * easeFactor).toInt()
        }
    }
}