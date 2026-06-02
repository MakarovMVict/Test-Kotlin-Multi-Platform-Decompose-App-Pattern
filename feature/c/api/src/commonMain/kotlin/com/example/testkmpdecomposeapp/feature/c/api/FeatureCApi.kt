package com.example.testkmpdecomposeapp.feature.c.api

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value

interface FeatureCApi {
    fun create(componentContext: ComponentContext): FeatureCComponent
}

interface FeatureCComponent {
    val stack: Value<ChildStack<*, Child>>

    fun openConfirm(itemId: Int)
    fun onItemClick(itemId: Int)
    fun onOpenConfirm()
    fun onDone()
    fun onBack()

    sealed interface Child {
        data object List : Child
        data class Details(val itemId: Int) : Child
        data class Confirm(val itemId: Int) : Child
    }
}
