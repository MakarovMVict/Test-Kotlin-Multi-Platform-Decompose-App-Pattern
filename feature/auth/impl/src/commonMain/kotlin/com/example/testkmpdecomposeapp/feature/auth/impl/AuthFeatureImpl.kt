package com.example.testkmpdecomposeapp.feature.auth.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.example.testkmpdecomposeapp.feature.auth.api.AuthFeatureApi
import org.koin.core.context.GlobalContext
import org.koin.core.parameter.parametersOf

class AuthFeatureImpl : AuthFeatureApi {
    @Composable
    override fun Screen(onOutput: (AuthFeatureApi.Output) -> Unit) {
        val viewModel = remember(onOutput) {
            GlobalContext.get().get<AuthViewModel> { parametersOf(onOutput) }
        }
        AuthScreen(viewModel = viewModel)
    }
}
