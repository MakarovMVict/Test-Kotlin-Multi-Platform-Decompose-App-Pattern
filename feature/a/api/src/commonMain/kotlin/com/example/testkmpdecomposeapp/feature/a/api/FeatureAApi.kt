package com.example.testkmpdecomposeapp.feature.a.api

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value

interface FeatureAApi {
    fun create(
        componentContext: ComponentContext,
        onOutput: (Output) -> Unit
    ): FeatureAComponent

    sealed interface Output {
        data class OpenFeatureCConfirm(val itemId: Int) : Output
    }
}

interface FeatureAComponent {
    val stack: Value<ChildStack<*, Child>>

    fun onOpenDetails(itemId: Int)
    fun onOpenFeatureCConfirm(itemId: Int)
    fun onBack()

    sealed interface Child {
        data object List : Child
        data class Details(val itemId: Int) : Child
    }
}
