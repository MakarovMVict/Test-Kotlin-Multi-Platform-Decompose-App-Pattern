package com.example.testkmpdecomposeapp.feature.b.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.example.testkmpdecomposeapp.feature.b.api.FeatureBApi
import com.example.testkmpdecomposeapp.feature.b.api.FeatureBComponent
import kotlinx.serialization.Serializable
import org.koin.core.context.GlobalContext
import org.koin.core.parameter.parametersOf

class FeatureBImpl : FeatureBApi {
    override fun create(componentContext: ComponentContext): FeatureBComponent =
        DefaultFeatureBComponent(componentContext)

    @Composable
    override fun Screen(component: FeatureBComponent) {
        Children(stack = component.stack) {
            when (val child = it.instance) {
                FeatureBComponent.Child.List -> {
                    val viewModel = remember(child) {
                        GlobalContext.get().get<FeatureBListViewModel> {
                            parametersOf(component::onOpenDetails)
                        }
                    }
                    FeatureBListScreen(viewModel = viewModel)
                }
                is FeatureBComponent.Child.Details -> {
                    val viewModel = remember(child) {
                        GlobalContext.get().get<FeatureBDetailsViewModel> {
                            parametersOf(child.itemId, component::onBack)
                        }
                    }
                    FeatureBDetailsScreen(viewModel = viewModel)
                }
            }
        }
    }
}

private class DefaultFeatureBComponent(componentContext: ComponentContext) :
    FeatureBComponent,
    ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, FeatureBComponent.Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.List,
        handleBackButton = true,
        childFactory = ::createChild
    )

    private fun createChild(config: Config, componentContext: ComponentContext): FeatureBComponent.Child {
        return when (config) {
            Config.List -> FeatureBComponent.Child.List
            is Config.Details -> FeatureBComponent.Child.Details(itemId = config.itemId)
        }
    }

    override fun onOpenDetails(itemId: Int) {
        navigation.push(Config.Details(itemId))
    }

    override fun onBack() {
        navigation.pop()
    }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object List : Config

        @Serializable
        data class Details(val itemId: Int) : Config
    }
}
