package com.example.testkmpdecomposeapp.feature.c.impl

import com.example.testkmpdecomposeapp.feature.c.api.FeatureCApi
import com.example.testkmpdecomposeapp.feature.c.api.presentation.FeatureCConfirmViewModel
import com.example.testkmpdecomposeapp.feature.c.api.presentation.FeatureCDetailsViewModel
import com.example.testkmpdecomposeapp.feature.c.api.presentation.FeatureCListViewModel
import com.example.testkmpdecomposeapp.feature.c.impl.data.FeatureCRepositoryImpl
import com.example.testkmpdecomposeapp.feature.c.impl.domain.ConfirmFeatureCItemUseCase
import com.example.testkmpdecomposeapp.feature.c.impl.domain.FeatureCRepository
import com.example.testkmpdecomposeapp.feature.c.impl.domain.GetFeatureCItemDetailsUseCase
import com.example.testkmpdecomposeapp.feature.c.impl.domain.GetFeatureCItemsUseCase
import com.example.testkmpdecomposeapp.feature.c.impl.presentation.FeatureCConfirmViewModelImpl
import com.example.testkmpdecomposeapp.feature.c.impl.presentation.FeatureCDetailsViewModelImpl
import com.example.testkmpdecomposeapp.feature.c.impl.presentation.FeatureCListViewModelImpl
import org.koin.dsl.module

val featureCModule = module {
    includes(featureCPlatformModule)

    single<FeatureCApi> { FeatureCImpl() }
    single<FeatureCRepository> { FeatureCRepositoryImpl() }
    factory { GetFeatureCItemsUseCase(repository = get()) }
    factory { GetFeatureCItemDetailsUseCase(repository = get()) }
    factory { ConfirmFeatureCItemUseCase(repository = get()) }

    factory<FeatureCListViewModel> { (onOpen: (Int) -> Unit) ->
        FeatureCListViewModelImpl(
            getItemsUseCase = get(),
            notificationManager = get(),
            onOpen = onOpen
        )
    }
    factory<FeatureCDetailsViewModel> { (itemId: Int, onBack: () -> Unit, onOpenConfirm: () -> Unit) ->
        FeatureCDetailsViewModelImpl(
            itemId = itemId,
            getItemDetailsUseCase = get(),
            onBack = onBack,
            onOpenConfirm = onOpenConfirm
        )
    }
    factory<FeatureCConfirmViewModel> { (itemId: Int, onBack: () -> Unit, onDone: () -> Unit) ->
        FeatureCConfirmViewModelImpl(
            itemId = itemId,
            getItemDetailsUseCase = get(),
            confirmItemUseCase = get(),
            onBack = onBack,
            onDone = onDone
        )
    }
}
