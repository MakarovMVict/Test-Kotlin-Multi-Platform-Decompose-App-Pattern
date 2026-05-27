package com.example.testkmpdecomposeapp.feature.a.impl

import com.example.testkmpdecomposeapp.feature.a.api.FeatureAApi
import com.example.testkmpdecomposeapp.feature.a.api.presentation.FeatureADetailsViewModel
import com.example.testkmpdecomposeapp.feature.a.api.presentation.FeatureAListViewModel
import com.example.testkmpdecomposeapp.feature.a.impl.presentation.FeatureADetailsViewModelImpl
import com.example.testkmpdecomposeapp.feature.a.impl.presentation.FeatureAListViewModelImpl
import org.koin.dsl.module

val featureAModule = module {
    single<FeatureAApi> { FeatureAImpl() }
    factory<FeatureAListViewModel> { (onOpen: (Int) -> Unit) ->
        FeatureAListViewModelImpl(onOpen = onOpen)
    }
    factory<FeatureADetailsViewModel> { (itemId: Int, onBack: () -> Unit, onOpenFeatureCConfirm: (Int) -> Unit) ->
        FeatureADetailsViewModelImpl(
            itemId = itemId,
            onBack = onBack,
            onOpenFeatureCConfirm = onOpenFeatureCConfirm
        )
    }
}
