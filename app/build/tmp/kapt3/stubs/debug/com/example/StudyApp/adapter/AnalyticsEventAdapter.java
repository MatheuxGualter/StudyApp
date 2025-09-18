package com.example.StudyApp.adapter;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\f\u0012\b\u0012\u00060\u0002R\u00020\u00000\u0001:\u0001\u0015B\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010\t\u001a\u00020\nH\u0016J\u001c\u0010\u000b\u001a\u00020\f2\n\u0010\r\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u000e\u001a\u00020\nH\u0016J\u001c\u0010\u000f\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\nH\u0016J\u0014\u0010\u0013\u001a\u00020\f2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\b0\u0007R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2 = {"Lcom/example/StudyApp/adapter/AnalyticsEventAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/example/StudyApp/adapter/AnalyticsEventAdapter$EventViewHolder;", "()V", "dateFormat", "Ljava/text/SimpleDateFormat;", "events", "", "Lcom/example/StudyApp/data/StudySessionEvent;", "getItemCount", "", "onBindViewHolder", "", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "updateEvents", "newEvents", "EventViewHolder", "app_debug"})
public final class AnalyticsEventAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.example.StudyApp.adapter.AnalyticsEventAdapter.EventViewHolder> {
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.example.StudyApp.data.StudySessionEvent> events;
    @org.jetbrains.annotations.NotNull()
    private final java.text.SimpleDateFormat dateFormat = null;
    
    public AnalyticsEventAdapter() {
        super();
    }
    
    public final void updateEvents(@org.jetbrains.annotations.NotNull()
    java.util.List<com.example.StudyApp.data.StudySessionEvent> newEvents) {
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public com.example.StudyApp.adapter.AnalyticsEventAdapter.EventViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    com.example.StudyApp.adapter.AnalyticsEventAdapter.EventViewHolder holder, int position) {
    }
    
    @java.lang.Override()
    public int getItemCount() {
        return 0;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000e"}, d2 = {"Lcom/example/StudyApp/adapter/AnalyticsEventAdapter$EventViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "(Lcom/example/StudyApp/adapter/AnalyticsEventAdapter;Landroid/view/View;)V", "resultIcon", "Landroid/widget/TextView;", "resultText", "timeText", "timestampText", "bind", "", "event", "Lcom/example/StudyApp/data/StudySessionEvent;", "app_debug"})
    public final class EventViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView resultIcon = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView resultText = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView timeText = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView timestampText = null;
        
        public EventViewHolder(@org.jetbrains.annotations.NotNull()
        android.view.View itemView) {
            super(null);
        }
        
        public final void bind(@org.jetbrains.annotations.NotNull()
        com.example.StudyApp.data.StudySessionEvent event) {
        }
    }
}