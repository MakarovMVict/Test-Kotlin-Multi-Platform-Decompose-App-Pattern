package com.example.testkmpdecomposeapp.feature.auth.api

interface AuthFeatureApi {
    sealed interface Output {
        data object Authorized : Output
    }
}
