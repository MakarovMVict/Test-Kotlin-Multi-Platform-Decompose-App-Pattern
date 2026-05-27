package com.example.testkmpdecomposeapp.android.features.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.testkmpdecomposeapp.feature.auth.api.AuthFeatureApi
import com.example.testkmpdecomposeapp.feature.auth.impl.presentation.AuthIntent
import com.example.testkmpdecomposeapp.feature.auth.impl.presentation.AuthViewModel
import org.koin.core.context.GlobalContext
import org.koin.core.parameter.parametersOf

@Composable
fun AuthFeatureScreen(onOutput: (AuthFeatureApi.Output) -> Unit) {
    val viewModel = remember(onOutput) {
        GlobalContext.get().get<AuthViewModel> { parametersOf(onOutput) }
    }

    val state by viewModel.uiState.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = state.title)
        Button(onClick = { viewModel.onIntent(AuthIntent.LoginClicked) }) {
            Text(state.loginButtonTitle)
        }
    }
}
