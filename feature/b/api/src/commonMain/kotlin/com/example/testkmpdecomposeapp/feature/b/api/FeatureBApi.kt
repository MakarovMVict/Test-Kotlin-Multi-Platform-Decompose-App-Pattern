package com.example.testkmpdecomposeapp.feature.b.api

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value

interface FeatureBApi {
    fun create(componentContext: ComponentContext): FeatureBComponent

    @Composable
    fun Screen(component: FeatureBComponent)
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
