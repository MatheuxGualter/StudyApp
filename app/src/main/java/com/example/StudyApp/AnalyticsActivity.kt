package com.example.StudyApp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.StudyApp.adapter.AnalyticsEventAdapter
import com.example.StudyApp.databinding.ActivityAnalyticsBinding
import com.example.StudyApp.ui.FlashcardViewModel
import kotlinx.coroutines.launch

class AnalyticsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAnalyticsBinding
    private lateinit var viewModel: FlashcardViewModel
    private lateinit var adapter: AnalyticsEventAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnalyticsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Analytics de Estudo"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        viewModel = ViewModelProvider(this)[FlashcardViewModel::class.java]
        setupRecyclerView()
        loadAnalytics()
    }

    private fun setupRecyclerView() {
        adapter = AnalyticsEventAdapter()
        binding.recentEventsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.recentEventsRecyclerView.adapter = adapter
    }

    private fun loadAnalytics() {
        lifecycleScope.launch {
            try {
                // Carregar estatísticas gerais
                val totalCorrect = viewModel.getTotalCorrectAnswers()
                val totalWrong = viewModel.getTotalWrongAnswers()
                val totalAnswers = viewModel.getTotalAnswers()
                val avgResponseTime = viewModel.getAverageResponseTimeForCorrect()
                val totalDecks = viewModel.getTotalDecksStudied()
                val totalFlashcards = viewModel.getTotalFlashcardsStudied()

                // Atualizar UI
                binding.totalCorrectText.text = totalCorrect.toString()
                binding.totalWrongText.text = totalWrong.toString()

                val accuracy = if (totalAnswers > 0) {
                    ((totalCorrect.toFloat() / totalAnswers) * 100).toInt()
                } else {
                    0
                }
                binding.accuracyText.text = "${accuracy}%"

                // Tempo médio de resposta
                if (avgResponseTime != null) {
                    val avgSeconds = avgResponseTime / 1000.0
                    binding.avgResponseTimeText.text = "${String.format("%.1f", avgSeconds)}s"
                } else {
                    binding.avgResponseTimeText.text = "N/A"
                }

                binding.totalDecksText.text = totalDecks.toString()
                binding.totalFlashcardsText.text = totalFlashcards.toString()

                // Mostrar/ocultar estado vazio
                if (totalAnswers == 0) {
                    showEmptyState()
                } else {
                    hideEmptyState()
                }

            } catch (e: Exception) {
                e.printStackTrace()
                showEmptyState()
            }
        }

        lifecycleScope.launch {
            viewModel.getAllStudyEvents().collect { events ->
                val recentEvents = events.take(10) // Mostrar apenas os 10 mais recentes
                adapter.updateEvents(recentEvents)
            }
        }
    }
        private fun showEmptyState() {
            binding.emptyStateCard.visibility = View.VISIBLE
            binding.recentEventsRecyclerView.visibility = View.GONE
        }

        private fun hideEmptyState() {
            binding.emptyStateCard.visibility = View.GONE
            binding.recentEventsRecyclerView.visibility = View.VISIBLE
        }

        override fun onSupportNavigateUp(): Boolean {
            onBackPressed()
            return true
        }
    }






