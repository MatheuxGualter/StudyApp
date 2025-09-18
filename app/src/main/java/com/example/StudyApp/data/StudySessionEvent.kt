package com.example.StudyApp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "study_session_events")
data class StudySessionEvent(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val timestamp: Long,
    val deckId: Long,
    val flashcardId: Long,
    val responseTimeMillis: Long,
    val isCorrect: Boolean,
    val latitude: Double?,
    val longitude: Double?
)



