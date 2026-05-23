package com.example.testkmpdecomposeapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.example.testkmpdecomposeapp.core.common.AppDestination
import com.example.testkmpdecomposeapp.core.common.AppNavigator
import com.example.testkmpdecomposeapp.core.common.MainTab
import com.example.testkmpdecomposeapp.feature.a.api.FeatureAApi
import com.example.testkmpdecomposeapp.feature.a.api.FeatureAComponent
import com.example.testkmpdecomposeapp.feature.b.api.FeatureBApi
import com.example.testkmpdecomposeapp.feature.b.api.FeatureBComponent
import com.example.testkmpdecomposeapp.feature.c.api.FeatureCApi
import com.example.testkmpdecomposeapp.feature.c.api.FeatureCComponent
import kotlinx.serialization.Serializable

class MainComponent(
    componentContext: ComponentContext,
    private val featureAApi: FeatureAApi,
    private val featureBApi: FeatureBApi,
    private val featureCApi: FeatureCApi,
    private val appNavigator: AppNavigator,
    initialDestination: AppDestination? = null
) : ComponentContext by componentContext {
    private val navigation = StackNavigation<Config>()
    var selectedTab by mutableStateOf(MainTab.A)
        private set

    val stack: Value<ChildStack<Config, Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.FeatureA,
        handleBackButton = false,
        childFactory = ::createChild
    )

    init {
        initialDestination?.let(::navigate)
    }

    fun onTabSelected(tab: MainTab) {
        navigation.bringToFront(tab.toConfig())
        selectedTab = tab
    }

    fun navigate(destination: AppDestination) {
        when (destination) {
            AppDestination.FeatureA -> onTabSelected(MainTab.A)
            AppDestination.FeatureB -> onTabSelected(MainTab.B)
            AppDestination.FeatureC -> onTabSelected(MainTab.C)
            is AppDestination.FeatureCConfirm -> {
                onTabSelected(MainTab.C)
                (stack.value.active.instance as? Child.FeatureC)?.component?.openConfirm(destination.itemId)
            }
            else -> Unit
        }
    }

    sealed interface Child {
        class FeatureA(val component: FeatureAComponent) : Child
        class FeatureB(val component: FeatureBComponent) : Child
        class FeatureC(val component: FeatureCComponent) : Child
    }

    private fun createChild(config: Config, componentContext: ComponentContext): Child {
        return when (config) {
            Config.FeatureA -> Child.FeatureA(
                featureAApi.create(
                    componentContext = componentContext,
                    onOutput = ::onFeatureAOutput
                )
            )
            Config.FeatureB -> Child.FeatureB(featureBApi.create(componentContext))
            Config.FeatureC -> Child.FeatureC(featureCApi.create(componentContext))
        }
    }

    private fun onFeatureAOutput(output: FeatureAApi.Output) {
        when (output) {
            is FeatureAApi.Output.OpenFeatureCConfirm ->
                appNavigator.navigate(AppDestination.FeatureCConfirm(output.itemId))
        }
    }

    @Serializable
    sealed interface Config {
        @Serializable
        data object FeatureA : Config

        @Serializable
        data object FeatureB : Config

        @Serializable
        data object FeatureC : Config
    }

    private fun MainTab.toConfig(): Config {
        return when (this) {
            MainTab.A -> Config.FeatureA
            MainTab.B -> Config.FeatureB
            MainTab.C -> Config.FeatureC
        }
    }
}
