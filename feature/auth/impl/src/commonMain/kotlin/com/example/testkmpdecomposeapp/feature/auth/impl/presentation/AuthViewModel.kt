package com.example.testkmpdecomposeapp.feature.auth.impl.presentation

import com.example.testkmpdecomposeapp.feature.auth.api.AuthFeatureApi
import com.example.testkmpdecomposeapp.feature.auth.api.presentation.AuthIntent
import com.example.testkmpdecomposeapp.feature.auth.api.presentation.AuthUiState
import com.example.testkmpdecomposeapp.feature.auth.api.presentation.AuthViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class AuthViewModelImpl(
    private val onAuthorized: (AuthFeatureApi.Output) -> Unit
) : AuthViewModel {
    private val _uiState = MutableStateFlow(AuthUiState())
    override val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    override fun onIntent(intent: AuthIntent) {
        when (intent) {
            AuthIntent.LoginClicked -> onAuthorized(AuthFeatureApi.Output.Authorized)
        }
    }

    override fun onLoginClicked() {
        onIntent(AuthIntent.LoginClicked)
    }
}
