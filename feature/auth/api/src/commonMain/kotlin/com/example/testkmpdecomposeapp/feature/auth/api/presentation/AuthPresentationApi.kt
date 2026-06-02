package com.example.testkmpdecomposeapp.feature.auth.api.presentation

import kotlinx.coroutines.flow.StateFlow

data class AuthUiState(
    val title: String = "Экран авторизации",
    val loginButtonTitle: String = "Войти"
)

sealed interface AuthIntent {
    data object LoginClicked : AuthIntent
}

interface AuthViewModel {
    val uiState: StateFlow<AuthUiState>
    fun onIntent(intent: AuthIntent)
    fun onLoginClicked()
}
