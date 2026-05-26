package com.example.testkmpdecomposeapp.android.features.b

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.example.testkmpdecomposeapp.feature.b.api.FeatureBComponent
import com.example.testkmpdecomposeapp.feature.b.impl.FeatureBDetailsViewModel
import com.example.testkmpdecomposeapp.feature.b.impl.FeatureBListViewModel
import org.koin.core.context.GlobalContext
import org.koin.core.parameter.parametersOf

@Composable
fun FeatureBScreen(component: FeatureBComponent) {
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
