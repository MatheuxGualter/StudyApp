package com.example.StudyApp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.StudyApp.databinding.ActivityAuthBinding
import androidx.activity.viewModels
import android.widget.Toast
import android.content.Intent
import androidx.core.view.isVisible
import com.example.StudyApp.ui.AuthState
import com.example.StudyApp.ui.AuthViewModel
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collectLatest

class AuthActivity : AppCompatActivity() {

	private lateinit var binding: ActivityAuthBinding
	private val viewModel: AuthViewModel by viewModels()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityAuthBinding.inflate(layoutInflater)
		setContentView(binding.root)

		binding.btnLogin.setOnClickListener {
			val email = binding.etEmail.text?.toString()?.trim().orEmpty()
			val password = binding.etPassword.text?.toString()?.trim().orEmpty()
			viewModel.login(email, password)
		}

		binding.btnRegister.setOnClickListener {
			val email = binding.etEmail.text?.toString()?.trim().orEmpty()
			val password = binding.etPassword.text?.toString()?.trim().orEmpty()
			viewModel.register(email, password)
		}

		lifecycleScope.launchWhenStarted {
			viewModel.state.collectLatest { state ->
				when (state) {
					is AuthState.Loading -> binding.progressBar.isVisible = true
					is AuthState.Success -> {
						binding.progressBar.isVisible = false
						navigateToHome()
					}
					is AuthState.Error -> {
						binding.progressBar.isVisible = false
						Toast.makeText(this@AuthActivity, state.message, Toast.LENGTH_LONG).show()
					}
					else -> Unit
				}
			}
		}

		// Se já estiver logado, navega direto
		viewModel.checkLoggedIn()
	}

	private fun navigateToHome() {
		startActivity(Intent(this, HomeActivity::class.java))
		finish()
	}
}


