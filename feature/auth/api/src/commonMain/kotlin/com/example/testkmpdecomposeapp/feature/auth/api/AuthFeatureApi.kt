package com.example.testkmpdecomposeapp.feature.auth.api

import androidx.compose.runtime.Composable

interface AuthFeatureApi {
    @Composable
    fun Screen(onOutput: (Output) -> Unit)

    sealed interface Output {
        data object Authorized : Output
    }
}
