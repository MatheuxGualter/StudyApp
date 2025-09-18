package com.example.StudyApp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.StudyApp.data.AuthRepository
import com.google.firebase.auth.FirebaseUser
import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class AuthState {
	object Idle : AuthState()
	object Loading : AuthState()
	data class Success(val user: FirebaseUser?) : AuthState()
	data class Error(val message: String) : AuthState()
}

class AuthViewModel(
	private val authRepository: AuthRepository = AuthRepository()
) : ViewModel() {

	private val _state = MutableStateFlow<AuthState>(AuthState.Idle)
	val state: StateFlow<AuthState> = _state.asStateFlow()

	fun checkLoggedIn() {
		val user = authRepository.getCurrentUser()
		if (user != null) {
			_state.value = AuthState.Success(user)
		}
	}

	fun login(email: String, password: String) {
		_state.value = AuthState.Loading
		viewModelScope.launch {
			try {
				authRepository.login(email, password)
				_state.value = AuthState.Success(authRepository.getCurrentUser())
			} catch (e: Exception) {
				_state.value = AuthState.Error(e.message ?: "Erro ao fazer login")
			}
		}
	}

	fun register(email: String, password: String) {
		_state.value = AuthState.Loading
		viewModelScope.launch {
			try {
				authRepository.register(email, password)
				_state.value = AuthState.Success(authRepository.getCurrentUser())
			} catch (e: Exception) {
				_state.value = AuthState.Error(e.message ?: "Erro ao cadastrar")
			}
		}
	}

    fun logout(context: Context) {
        authRepository.logout(context)
    }
}



