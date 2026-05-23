package com.example.testkmpdecomposeapp.core.common

sealed interface AppDestination {
    data object Splash : AppDestination
    data object Auth : AppDestination
    data object Main : AppDestination
    data object FeatureA : AppDestination
    data object FeatureB : AppDestination
    data object FeatureC : AppDestination
    data class FeatureCConfirm(val itemId: Int) : AppDestination
}

enum class MainTab {
    A,
    B,
    C
}
