package com.example.StudyApp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.StudyApp.R
import com.example.StudyApp.data.StudySessionEvent
import java.text.SimpleDateFormat
import java.util.*

class AnalyticsEventAdapter : RecyclerView.Adapter<AnalyticsEventAdapter.EventViewHolder>() {
    
    private var events: List<StudySessionEvent> = emptyList()
    private val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    
    fun updateEvents(newEvents: List<StudySessionEvent>) {
        events = newEvents
        notifyDataSetChanged()
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_analytics_event, parent, false)
        return EventViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(events[position])
    }
    
    override fun getItemCount(): Int = events.size
    
    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val resultIcon: TextView = itemView.findViewById(R.id.resultIcon)
        private val resultText: TextView = itemView.findViewById(R.id.resultText)
        private val timeText: TextView = itemView.findViewById(R.id.timeText)
        private val timestampText: TextView = itemView.findViewById(R.id.timestampText)
        
        fun bind(event: StudySessionEvent) {
            if (event.isCorrect) {
                resultIcon.text = "✅"
                resultText.text = "Acerto"
                resultText.setTextColor(itemView.context.getColor(android.R.color.holo_green_dark))
            } else {
                resultIcon.text = "❌"
                resultText.text = "Erro"
                resultText.setTextColor(itemView.context.getColor(android.R.color.holo_red_dark))
            }
            
            val responseTimeSeconds = event.responseTimeMillis / 1000.0
            timeText.text = "${String.format("%.1f", responseTimeSeconds)}s"
            
            val date = Date(event.timestamp)
            timestampText.text = dateFormat.format(date)
        }
    }
}

