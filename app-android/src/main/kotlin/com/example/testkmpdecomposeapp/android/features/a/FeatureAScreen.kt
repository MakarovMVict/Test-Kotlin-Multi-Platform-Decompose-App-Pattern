package com.example.testkmpdecomposeapp.android.features.a

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.example.testkmpdecomposeapp.feature.a.api.FeatureAComponent
import com.example.testkmpdecomposeapp.feature.a.impl.FeatureADetailsViewModel
import com.example.testkmpdecomposeapp.feature.a.impl.FeatureAListViewModel
import org.koin.core.context.GlobalContext
import org.koin.core.parameter.parametersOf

@Composable
fun FeatureAScreen(component: FeatureAComponent) {
    Children(stack = component.stack) {
        when (val child = it.instance) {
            FeatureAComponent.Child.List -> {
                val viewModel = remember(child) {
                    GlobalContext.get().get<FeatureAListViewModel> {
                        parametersOf(component::onOpenDetails)
                    }
                }
                FeatureAListScreen(viewModel = viewModel)
            }
            is FeatureAComponent.Child.Details -> {
                val viewModel = remember(child) {
                    GlobalContext.get().get<FeatureADetailsViewModel> {
                        parametersOf(
                            child.itemId,
                            component::onBack,
                            component::onOpenFeatureCConfirm
                        )
                    }
                }
                FeatureADetailsScreen(viewModel = viewModel)
            }
        }
    }
}
