package com.example.testkmpdecomposeapp.feature.auth.impl

import com.example.testkmpdecomposeapp.feature.auth.api.AuthFeatureApi
import com.example.testkmpdecomposeapp.feature.auth.api.presentation.AuthViewModel
import com.example.testkmpdecomposeapp.feature.auth.impl.presentation.AuthViewModelImpl
import org.koin.dsl.module

val authFeatureModule = module {
    single<AuthFeatureApi> { AuthFeatureImpl() }
    factory<AuthViewModel> { (onAuthorized: (AuthFeatureApi.Output) -> Unit) ->
        AuthViewModelImpl(onAuthorized = onAuthorized)
    }
}
