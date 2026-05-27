package com.example.testkmpdecomposeapp.feature.a.impl

import com.example.testkmpdecomposeapp.feature.a.api.FeatureAApi
import com.example.testkmpdecomposeapp.feature.a.impl.presentation.FeatureADetailsViewModel
import com.example.testkmpdecomposeapp.feature.a.impl.presentation.FeatureAListViewModel
import org.koin.dsl.module

val featureAModule = module {
    single<FeatureAApi> { FeatureAImpl() }
    factory { (onOpen: (Int) -> Unit) ->
        FeatureAListViewModel(onOpen = onOpen)
    }
    factory { (itemId: Int, onBack: () -> Unit, onOpenFeatureCConfirm: (Int) -> Unit) ->
        FeatureADetailsViewModel(
            itemId = itemId,
            onBack = onBack,
            onOpenFeatureCConfirm = onOpenFeatureCConfirm
        )
    }
}
