package com.example.StudyApp.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001\u0017B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0002J\u0010\u0010\f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\nH\u0002J$\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010\r\u001a\u00020\n2\u0006\u0010\u0011\u001a\u00020\u0012H\u0086@\u00a2\u0006\u0002\u0010\u0013J\u0016\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u000f2\u0006\u0010\u0016\u001a\u00020\nH\u0002R\u001b\u0010\u0003\u001a\u00020\u00048BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0018"}, d2 = {"Lcom/example/StudyApp/data/AIRepository;", "", "()V", "model", "Lcom/google/ai/client/generativeai/GenerativeModel;", "getModel", "()Lcom/google/ai/client/generativeai/GenerativeModel;", "model$delegate", "Lkotlin/Lazy;", "buildPrompt", "", "userText", "extractJsonArray", "text", "generateFlashcardsFromText", "", "Lcom/example/StudyApp/data/Flashcard;", "deckId", "", "(Ljava/lang/String;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "parseFlashcardsJson", "Lcom/example/StudyApp/data/AIRepository$FlashcardItem;", "raw", "FlashcardItem", "app_debug"})
public final class AIRepository {
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy model$delegate = null;
    
    public AIRepository() {
        super();
    }
    
    private final com.google.ai.client.generativeai.GenerativeModel getModel() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object generateFlashcardsFromText(@org.jetbrains.annotations.NotNull()
    java.lang.String text, long deckId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.StudyApp.data.Flashcard>> $completion) {
        return null;
    }
    
    private final java.lang.String buildPrompt(java.lang.String userText) {
        return null;
    }
    
    private final java.util.List<com.example.StudyApp.data.AIRepository.FlashcardItem> parseFlashcardsJson(java.lang.String raw) {
        return null;
    }
    
    private final java.lang.String extractJsonArray(java.lang.String text) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0082\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\n\u001a\u00020\u0003H\u00c6\u0003J\u001d\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u000f\u001a\u00020\u0010H\u00d6\u0001J\t\u0010\u0011\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007\u00a8\u0006\u0012"}, d2 = {"Lcom/example/StudyApp/data/AIRepository$FlashcardItem;", "", "front", "", "back", "(Ljava/lang/String;Ljava/lang/String;)V", "getBack", "()Ljava/lang/String;", "getFront", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
    static final class FlashcardItem {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String front = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String back = null;
        
        public FlashcardItem(@org.jetbrains.annotations.NotNull()
        java.lang.String front, @org.jetbrains.annotations.NotNull()
        java.lang.String back) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getFront() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getBack() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.example.StudyApp.data.AIRepository.FlashcardItem copy(@org.jetbrains.annotations.NotNull()
        java.lang.String front, @org.jetbrains.annotations.NotNull()
        java.lang.String back) {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String toString() {
            return null;
        }
    }
}