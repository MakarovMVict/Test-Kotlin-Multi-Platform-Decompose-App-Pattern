package com.example.testkmpdecomposeapp.feature.a.impl

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.example.testkmpdecomposeapp.feature.a.api.FeatureAApi
import com.example.testkmpdecomposeapp.feature.a.api.FeatureAComponent
import kotlinx.serialization.Serializable

class FeatureAImpl : FeatureAApi {
    override fun create(
        componentContext: ComponentContext,
        onOutput: (FeatureAApi.Output) -> Unit
    ): FeatureAComponent = DefaultFeatureAComponent(
        componentContext = componentContext,
        onOutput = onOutput
    )

}

private class DefaultFeatureAComponent(
    componentContext: ComponentContext,
    private val onOutput: (FeatureAApi.Output) -> Unit
) :
    FeatureAComponent,
    ComponentContext by componentContext {
    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, FeatureAComponent.Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.List,
        handleBackButton = true,
        childFactory = ::createChild
    )

    private fun createChild(config: Config, componentContext: ComponentContext): FeatureAComponent.Child {
        return when (config) {
            Config.List -> FeatureAComponent.Child.List
            is Config.Details -> FeatureAComponent.Child.Details(itemId = config.itemId)
        }
    }

    override fun onOpenDetails(itemId: Int) {
        navigation.push(Config.Details(itemId))
    }

    override fun onBack() {
        navigation.pop()
    }

    override fun onOpenFeatureCConfirm(itemId: Int) {
        onOutput(FeatureAApi.Output.OpenFeatureCConfirm(itemId))
    }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object List : Config

        @Serializable
        data class Details(val itemId: Int) : Config
    }
}
