package com.example.StudyApp.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001J\u0014\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003H\'J\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u001c\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u00032\u0006\u0010\n\u001a\u00020\u000bH\'J$\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u00032\u0006\u0010\r\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u000bH\'J\u000e\u0010\u000f\u001a\u00020\u0010H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u000e\u0010\u0011\u001a\u00020\u0010H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u000e\u0010\u0012\u001a\u00020\u0010H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u000e\u0010\u0013\u001a\u00020\u0010H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u000e\u0010\u0014\u001a\u00020\u0010H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u0016\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0018\u00a8\u0006\u0019"}, d2 = {"Lcom/example/StudyApp/data/StudySessionDao;", "", "getAllEvents", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/example/StudyApp/data/StudySessionEvent;", "getAverageResponseTimeForCorrect", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getEventsForDeck", "deckId", "", "getEventsInRange", "startTime", "endTime", "getTotalAnswers", "", "getTotalCorrectAnswers", "getTotalDecksStudied", "getTotalFlashcardsStudied", "getTotalWrongAnswers", "insert", "", "event", "(Lcom/example/StudyApp/data/StudySessionEvent;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao()
public abstract interface StudySessionDao {
    
    @androidx.room.Insert()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.example.StudyApp.data.StudySessionEvent event, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM study_session_events ORDER BY timestamp DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.StudyApp.data.StudySessionEvent>> getAllEvents();
    
    @androidx.room.Query(value = "SELECT * FROM study_session_events WHERE deckId = :deckId ORDER BY timestamp DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.StudyApp.data.StudySessionEvent>> getEventsForDeck(long deckId);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM study_session_events WHERE isCorrect = 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getTotalCorrectAnswers(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM study_session_events WHERE isCorrect = 0")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getTotalWrongAnswers(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Query(value = "SELECT AVG(responseTimeMillis) FROM study_session_events WHERE isCorrect = 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAverageResponseTimeForCorrect(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Double> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(DISTINCT deckId) FROM study_session_events")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getTotalDecksStudied(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(DISTINCT flashcardId) FROM study_session_events")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getTotalFlashcardsStudied(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM study_session_events")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getTotalAnswers(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM study_session_events WHERE timestamp >= :startTime AND timestamp <= :endTime ORDER BY timestamp DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.StudyApp.data.StudySessionEvent>> getEventsInRange(long startTime, long endTime);
}