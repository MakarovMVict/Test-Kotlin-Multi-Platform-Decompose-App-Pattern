package com.example.testkmpdecomposeapp.feature.b.api

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value

interface FeatureBApi {
    fun create(componentContext: ComponentContext): FeatureBComponent
}

interface FeatureBComponent {
    val stack: Value<ChildStack<*, Child>>

    fun onOpenDetails(itemId: Int)
    fun onBack()

    sealed interface Child {
        data object List : Child
        data class Details(val itemId: Int) : Child
    }
}
