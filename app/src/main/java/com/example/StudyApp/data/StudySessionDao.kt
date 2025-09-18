package com.example.StudyApp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface StudySessionDao {
    @Insert
    suspend fun insert(event: StudySessionEvent)

    @Query("SELECT * FROM study_session_events ORDER BY timestamp DESC")
    fun getAllEvents(): Flow<List<StudySessionEvent>>

    @Query("SELECT * FROM study_session_events WHERE deckId = :deckId ORDER BY timestamp DESC")
    fun getEventsForDeck(deckId: Long): Flow<List<StudySessionEvent>>

    @Query("SELECT COUNT(*) FROM study_session_events WHERE isCorrect = 1")
    suspend fun getTotalCorrectAnswers(): Int

    @Query("SELECT COUNT(*) FROM study_session_events WHERE isCorrect = 0")
    suspend fun getTotalWrongAnswers(): Int

    @Query("SELECT AVG(responseTimeMillis) FROM study_session_events WHERE isCorrect = 1")
    suspend fun getAverageResponseTimeForCorrect(): Double?

    @Query("SELECT COUNT(DISTINCT deckId) FROM study_session_events")
    suspend fun getTotalDecksStudied(): Int

    @Query("SELECT COUNT(DISTINCT flashcardId) FROM study_session_events")
    suspend fun getTotalFlashcardsStudied(): Int

    @Query("SELECT COUNT(*) FROM study_session_events")
    suspend fun getTotalAnswers(): Int

    @Query("SELECT * FROM study_session_events WHERE timestamp >= :startTime AND timestamp <= :endTime ORDER BY timestamp DESC")
    fun getEventsInRange(startTime: Long, endTime: Long): Flow<List<StudySessionEvent>>
}