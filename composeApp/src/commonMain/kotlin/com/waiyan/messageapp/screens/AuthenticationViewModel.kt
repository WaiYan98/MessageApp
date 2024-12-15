package com.waiyan.messageapp.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.waiyan.messageapp.remote.AuthenticationService
import dev.gitlive.firebase.auth.FirebaseAuthUserCollisionException
import dev.gitlive.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthenticationViewModel(
    private val authenticationService: AuthenticationService
) : ViewModel() {

    init {
        getCurrentUser()
    }

    private val _uiState = MutableStateFlow(AuthenticationUiState())
    val uiState: StateFlow<AuthenticationUiState> = _uiState

    fun createUser(email: String, password: String) = viewModelScope.launch {
        _uiState.emit(AuthenticationUiState(isLoading = true))
        if (inputValidator(email, password)) {
            try {
                val user = authenticationService.createUser(email, password)
                _uiState.emit(AuthenticationUiState(success = user))
            } catch (e: FirebaseAuthUserCollisionException) {
                signInUser(email, password)
            } catch (e: Exception) {
                _uiState.emit(AuthenticationUiState(onError = e.message))
            }
        } else {
            _uiState.emit(AuthenticationUiState(onError = "Invalid Email or Password"))
        }
    }

    private fun signInUser(email: String, password: String) = viewModelScope.launch {
        _uiState.emit(AuthenticationUiState(isLoading = true))
        try {
            val user = authenticationService.signIn(email, password)
            _uiState.emit(AuthenticationUiState(success = user))
        } catch (e: Exception) {
            _uiState.emit(AuthenticationUiState(onError = e.message))
        }
    }

    private fun getCurrentUser() = viewModelScope.launch {
        val user = authenticationService.getCurrentUser()
        if (user != null) {
            _uiState.emit(AuthenticationUiState(success = user))
        }
    }

    private fun inputValidator(email: String, password: String): Boolean {
        val regex = Regex("^[A-Za-z0-9.]+@gmail.com")
        val checkResultOfEmail = regex.matches(email)
        val checkResultOfPassword = password.length >= 8
        return checkResultOfEmail && checkResultOfPassword
    }
}