package com.example.testkmpdecomposeapp.feature.auth.impl

import com.example.testkmpdecomposeapp.feature.auth.api.AuthFeatureApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class AuthUiState(
    val title: String = "Экран авторизации",
    val loginButtonTitle: String = "Войти"
)

sealed interface AuthIntent {
    data object LoginClicked : AuthIntent
}

class AuthViewModel(
    private val onAuthorized: (AuthFeatureApi.Output) -> Unit
) {
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun onIntent(intent: AuthIntent) {
        when (intent) {
            AuthIntent.LoginClicked -> onAuthorized(AuthFeatureApi.Output.Authorized)
        }
    }
}
