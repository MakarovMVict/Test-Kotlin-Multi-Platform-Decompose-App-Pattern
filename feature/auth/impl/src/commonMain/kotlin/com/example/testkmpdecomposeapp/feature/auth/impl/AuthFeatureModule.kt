package com.example.testkmpdecomposeapp.feature.auth.impl

import com.example.testkmpdecomposeapp.feature.auth.api.AuthFeatureApi
import com.example.testkmpdecomposeapp.feature.auth.impl.presentation.AuthViewModel
import org.koin.dsl.module

val authFeatureModule = module {
    single<AuthFeatureApi> { AuthFeatureImpl() }
    factory { (onAuthorized: (AuthFeatureApi.Output) -> Unit) ->
        AuthViewModel(onAuthorized = onAuthorized)
    }
}
