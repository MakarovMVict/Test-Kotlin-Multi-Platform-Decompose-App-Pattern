package com.example.testkmpdecomposeapp

import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.example.testkmpdecomposeapp.core.common.MainTab
import com.example.testkmpdecomposeapp.feature.a.api.FeatureAComponent
import com.example.testkmpdecomposeapp.feature.a.impl.FeatureADetailsIntent
import com.example.testkmpdecomposeapp.feature.a.impl.FeatureADetailsViewModel
import com.example.testkmpdecomposeapp.feature.a.impl.FeatureAListIntent
import com.example.testkmpdecomposeapp.feature.a.impl.FeatureAListViewModel
import com.example.testkmpdecomposeapp.feature.auth.api.AuthFeatureApi
import com.example.testkmpdecomposeapp.feature.auth.impl.AuthIntent
import com.example.testkmpdecomposeapp.feature.auth.impl.AuthViewModel
import com.example.testkmpdecomposeapp.feature.b.api.FeatureBComponent
import com.example.testkmpdecomposeapp.feature.b.impl.FeatureBDetailsIntent
import com.example.testkmpdecomposeapp.feature.b.impl.FeatureBDetailsViewModel
import com.example.testkmpdecomposeapp.feature.b.impl.FeatureBListIntent
import com.example.testkmpdecomposeapp.feature.b.impl.FeatureBListViewModel
import com.example.testkmpdecomposeapp.feature.c.api.FeatureCComponent
import com.example.testkmpdecomposeapp.feature.c.impl.FeatureCConfirmIntent
import com.example.testkmpdecomposeapp.feature.c.impl.FeatureCConfirmViewModel
import com.example.testkmpdecomposeapp.feature.c.impl.FeatureCDetailsIntent
import com.example.testkmpdecomposeapp.feature.c.impl.FeatureCDetailsViewModel
import com.example.testkmpdecomposeapp.feature.c.impl.FeatureCListIntent
import com.example.testkmpdecomposeapp.feature.c.impl.FeatureCListViewModel
import org.koin.core.parameter.parametersOf

data class IosAppState(
    val root: String,
    val selectedTab: String,
    val activeScreen: String,
    val activeItemId: Int
)

interface IosAppStateListener {
    fun onStateChanged(state: IosAppState)
}

class IosAuthScreenViewModel internal constructor(
    private val delegate: AuthViewModel,
    private val onStateChanged: () -> Unit
) {
    val title: String get() = delegate.uiState.value.title
    val loginButtonTitle: String get() = delegate.uiState.value.loginButtonTitle

    fun onLoginClicked() {
        delegate.onIntent(AuthIntent.LoginClicked)
        onStateChanged()
    }
}

class IosFeatureAListScreenViewModel internal constructor(
    private val delegate: FeatureAListViewModel,
    private val onStateChanged: () -> Unit
) {
    val title: String get() = delegate.uiState.value.title

    fun itemCount(): Int = delegate.uiState.value.items.size
    fun itemAt(index: Int): Int = delegate.uiState.value.items[index]

    fun onItemClicked(itemId: Int) {
        delegate.onIntent(FeatureAListIntent.OpenItem(itemId))
        onStateChanged()
    }
}

class IosFeatureADetailsScreenViewModel internal constructor(
    private val delegate: FeatureADetailsViewModel,
    private val onStateChanged: () -> Unit
) {
    val title: String get() = delegate.uiState.value.title

    fun onOpenFeatureCConfirmClicked() {
        delegate.onIntent(FeatureADetailsIntent.OpenFeatureCConfirmClicked)
        onStateChanged()
    }

    fun onBackClicked() {
        delegate.onIntent(FeatureADetailsIntent.BackClicked)
        onStateChanged()
    }
}

class IosFeatureBListScreenViewModel internal constructor(
    private val delegate: FeatureBListViewModel,
    private val onStateChanged: () -> Unit
) {
    val title: String get() = delegate.uiState.value.title

    fun itemCount(): Int = delegate.uiState.value.items.size
    fun itemAt(index: Int): Int = delegate.uiState.value.items[index]

    fun onItemClicked(itemId: Int) {
        delegate.onIntent(FeatureBListIntent.OpenItem(itemId))
        onStateChanged()
    }
}

class IosFeatureBDetailsScreenViewModel internal constructor(
    private val delegate: FeatureBDetailsViewModel,
    private val onStateChanged: () -> Unit
) {
    val title: String get() = delegate.uiState.value.title

    fun onBackClicked() {
        delegate.onIntent(FeatureBDetailsIntent.BackClicked)
        onStateChanged()
    }
}

class IosFeatureCListScreenViewModel internal constructor(
    private val delegate: FeatureCListViewModel,
    private val onStateChanged: () -> Unit
) {
    val title: String get() = delegate.uiState.value.title

    fun itemCount(): Int = delegate.uiState.value.items.size
    fun itemAt(index: Int): Int = delegate.uiState.value.items[index]

    fun onItemClicked(itemId: Int) {
        delegate.onIntent(FeatureCListIntent.OpenItem(itemId))
        onStateChanged()
    }
}

class IosFeatureCDetailsScreenViewModel internal constructor(
    private val delegate: FeatureCDetailsViewModel,
    private val onStateChanged: () -> Unit
) {
    val title: String get() = delegate.uiState.value.title

    fun onOpenConfirmClicked() {
        delegate.onIntent(FeatureCDetailsIntent.OpenConfirmClicked)
        onStateChanged()
    }

    fun onBackClicked() {
        delegate.onIntent(FeatureCDetailsIntent.BackClicked)
        onStateChanged()
    }
}

class IosFeatureCConfirmScreenViewModel internal constructor(
    private val delegate: FeatureCConfirmViewModel,
    private val onStateChanged: () -> Unit
) {
    val title: String get() = delegate.uiState.value.title
    val canComplete: Boolean get() = delegate.uiState.value.canComplete

    fun onDoneClicked() {
        delegate.onIntent(FeatureCConfirmIntent.DoneClicked)
        onStateChanged()
    }

    fun onBackClicked() {
        delegate.onIntent(FeatureCConfirmIntent.BackClicked)
        onStateChanged()
    }
}

class IosAppBridge {
    private val lifecycle = LifecycleRegistry()
    private val root: AppRootComponent
    private var listener: IosAppStateListener? = null

    private var authOwner: AppRootComponent.Child.Auth? = null
    private var authVm: IosAuthScreenViewModel? = null

    private var featureAListOwner: FeatureAComponent? = null
    private var featureAListVm: IosFeatureAListScreenViewModel? = null
    private var featureADetailsOwner: FeatureAComponent? = null
    private var featureADetailsItemId: Int? = null
    private var featureADetailsVm: IosFeatureADetailsScreenViewModel? = null

    private var featureBListOwner: FeatureBComponent? = null
    private var featureBListVm: IosFeatureBListScreenViewModel? = null
    private var featureBDetailsOwner: FeatureBComponent? = null
    private var featureBDetailsItemId: Int? = null
    private var featureBDetailsVm: IosFeatureBDetailsScreenViewModel? = null

    private var featureCListOwner: FeatureCComponent? = null
    private var featureCListVm: IosFeatureCListScreenViewModel? = null
    private var featureCDetailsOwner: FeatureCComponent? = null
    private var featureCDetailsItemId: Int? = null
    private var featureCDetailsVm: IosFeatureCDetailsScreenViewModel? = null
    private var featureCConfirmOwner: FeatureCComponent? = null
    private var featureCConfirmItemId: Int? = null
    private var featureCConfirmVm: IosFeatureCConfirmScreenViewModel? = null

    init {
        initKoin()
        root = AppRootComponent(DefaultComponentContext(lifecycle = lifecycle))
    }

    fun setListener(listener: IosAppStateListener?) {
        this.listener = listener
        notifyState()
    }

    fun getState(): IosAppState = buildState()

    fun onSplashFinished() {
        val child = root.stack.value.active.instance as? AppRootComponent.Child.Splash ?: return
        child.onFinished()
        notifyState()
    }

    fun authViewModel(): IosAuthScreenViewModel? {
        val child = root.stack.value.active.instance as? AppRootComponent.Child.Auth ?: return null
        if (authOwner !== child || authVm == null) {
            val vm = getKoinInstance<AuthViewModel> { parametersOf(child.onOutput) }
            authOwner = child
            authVm = IosAuthScreenViewModel(vm, ::notifyState)
        }
        return authVm
    }

    fun selectTabA() {
        currentMainComponent()?.onTabSelected(MainTab.A)
        notifyState()
    }

    fun selectTabB() {
        currentMainComponent()?.onTabSelected(MainTab.B)
        notifyState()
    }

    fun selectTabC() {
        currentMainComponent()?.onTabSelected(MainTab.C)
        notifyState()
    }

    fun featureAListViewModel(): IosFeatureAListScreenViewModel? {
        val child = currentMainChild() as? MainComponent.Child.FeatureA ?: return null
        val component = child.component
        if (component.stack.value.active.instance != FeatureAComponent.Child.List) return null

        if (featureAListOwner !== component || featureAListVm == null) {
            val vm = getKoinInstance<FeatureAListViewModel> { parametersOf(component::onOpenDetails) }
            featureAListOwner = component
            featureAListVm = IosFeatureAListScreenViewModel(vm, ::notifyState)
        }
        return featureAListVm
    }

    fun featureADetailsViewModel(): IosFeatureADetailsScreenViewModel? {
        val child = currentMainChild() as? MainComponent.Child.FeatureA ?: return null
        val component = child.component
        val details = component.stack.value.active.instance as? FeatureAComponent.Child.Details ?: return null

        if (featureADetailsOwner !== component || featureADetailsItemId != details.itemId || featureADetailsVm == null) {
            val vm = getKoinInstance<FeatureADetailsViewModel> {
                parametersOf(details.itemId, component::onBack, component::onOpenFeatureCConfirm)
            }
            featureADetailsOwner = component
            featureADetailsItemId = details.itemId
            featureADetailsVm = IosFeatureADetailsScreenViewModel(vm, ::notifyState)
        }
        return featureADetailsVm
    }

    fun featureBListViewModel(): IosFeatureBListScreenViewModel? {
        val child = currentMainChild() as? MainComponent.Child.FeatureB ?: return null
        val component = child.component
        if (component.stack.value.active.instance != FeatureBComponent.Child.List) return null

        if (featureBListOwner !== component || featureBListVm == null) {
            val vm = getKoinInstance<FeatureBListViewModel> { parametersOf(component::onOpenDetails) }
            featureBListOwner = component
            featureBListVm = IosFeatureBListScreenViewModel(vm, ::notifyState)
        }
        return featureBListVm
    }

    fun featureBDetailsViewModel(): IosFeatureBDetailsScreenViewModel? {
        val child = currentMainChild() as? MainComponent.Child.FeatureB ?: return null
        val component = child.component
        val details = component.stack.value.active.instance as? FeatureBComponent.Child.Details ?: return null

        if (featureBDetailsOwner !== component || featureBDetailsItemId != details.itemId || featureBDetailsVm == null) {
            val vm = getKoinInstance<FeatureBDetailsViewModel> {
                parametersOf(details.itemId, component::onBack)
            }
            featureBDetailsOwner = component
            featureBDetailsItemId = details.itemId
            featureBDetailsVm = IosFeatureBDetailsScreenViewModel(vm, ::notifyState)
        }
        return featureBDetailsVm
    }

    fun featureCListViewModel(): IosFeatureCListScreenViewModel? {
        val child = currentMainChild() as? MainComponent.Child.FeatureC ?: return null
        val component = child.component
        if (component.stack.value.active.instance != FeatureCComponent.Child.List) return null

        if (featureCListOwner !== component || featureCListVm == null) {
            val vm = getKoinInstance<FeatureCListViewModel> { parametersOf(component::onItemClick) }
            featureCListOwner = component
            featureCListVm = IosFeatureCListScreenViewModel(vm, ::notifyState)
        }
        return featureCListVm
    }

    fun featureCDetailsViewModel(): IosFeatureCDetailsScreenViewModel? {
        val child = currentMainChild() as? MainComponent.Child.FeatureC ?: return null
        val component = child.component
        val details = component.stack.value.active.instance as? FeatureCComponent.Child.Details ?: return null

        if (featureCDetailsOwner !== component || featureCDetailsItemId != details.itemId || featureCDetailsVm == null) {
            val vm = getKoinInstance<FeatureCDetailsViewModel> {
                parametersOf(details.itemId, component::onBack, component::onOpenConfirm)
            }
            featureCDetailsOwner = component
            featureCDetailsItemId = details.itemId
            featureCDetailsVm = IosFeatureCDetailsScreenViewModel(vm, ::notifyState)
        }
        return featureCDetailsVm
    }

    fun featureCConfirmViewModel(): IosFeatureCConfirmScreenViewModel? {
        val child = currentMainChild() as? MainComponent.Child.FeatureC ?: return null
        val component = child.component
        val confirm = component.stack.value.active.instance as? FeatureCComponent.Child.Confirm ?: return null

        if (featureCConfirmOwner !== component || featureCConfirmItemId != confirm.itemId || featureCConfirmVm == null) {
            val vm = getKoinInstance<FeatureCConfirmViewModel> {
                parametersOf(confirm.itemId, component::onBack, component::onDone)
            }
            featureCConfirmOwner = component
            featureCConfirmItemId = confirm.itemId
            featureCConfirmVm = IosFeatureCConfirmScreenViewModel(vm, ::notifyState)
        }
        return featureCConfirmVm
    }

    private fun notifyState() {
        listener?.onStateChanged(buildState())
    }

    private fun currentMainComponent(): MainComponent? {
        val child = root.stack.value.active.instance as? AppRootComponent.Child.Main ?: return null
        return child.component
    }

    private fun currentMainChild(): MainComponent.Child? {
        val component = currentMainComponent() ?: return null
        return component.stack.value.active.instance
    }

    private fun buildState(): IosAppState {
        val rootChild = root.stack.value.active.instance
        return when (rootChild) {
            is AppRootComponent.Child.Splash -> IosAppState(
                root = "splash",
                selectedTab = "a",
                activeScreen = "list",
                activeItemId = 0
            )
            is AppRootComponent.Child.Auth -> IosAppState(
                root = "auth",
                selectedTab = "a",
                activeScreen = "list",
                activeItemId = 0
            )
            is AppRootComponent.Child.Main -> {
                val tab = rootChild.component.selectedTab.toIosTab()
                val child = rootChild.component.stack.value.active.instance

                when (child) {
                    is MainComponent.Child.FeatureA -> mapFeatureAState(tab, child.component)
                    is MainComponent.Child.FeatureB -> mapFeatureBState(tab, child.component)
                    is MainComponent.Child.FeatureC -> mapFeatureCState(tab, child.component)
                }
            }
        }
    }

    private fun mapFeatureAState(
        tab: String,
        component: FeatureAComponent
    ): IosAppState {
        return when (val child = component.stack.value.active.instance) {
            FeatureAComponent.Child.List -> IosAppState(
                root = "main",
                selectedTab = tab,
                activeScreen = "list",
                activeItemId = 0
            )
            is FeatureAComponent.Child.Details -> IosAppState(
                root = "main",
                selectedTab = tab,
                activeScreen = "details",
                activeItemId = child.itemId
            )
        }
    }

    private fun mapFeatureBState(
        tab: String,
        component: FeatureBComponent
    ): IosAppState {
        return when (val child = component.stack.value.active.instance) {
            FeatureBComponent.Child.List -> IosAppState(
                root = "main",
                selectedTab = tab,
                activeScreen = "list",
                activeItemId = 0
            )
            is FeatureBComponent.Child.Details -> IosAppState(
                root = "main",
                selectedTab = tab,
                activeScreen = "details",
                activeItemId = child.itemId
            )
        }
    }

    private fun mapFeatureCState(
        tab: String,
        component: FeatureCComponent
    ): IosAppState {
        return when (val child = component.stack.value.active.instance) {
            FeatureCComponent.Child.List -> IosAppState(
                root = "main",
                selectedTab = tab,
                activeScreen = "list",
                activeItemId = 0
            )
            is FeatureCComponent.Child.Details -> IosAppState(
                root = "main",
                selectedTab = tab,
                activeScreen = "details",
                activeItemId = child.itemId
            )
            is FeatureCComponent.Child.Confirm -> IosAppState(
                root = "main",
                selectedTab = tab,
                activeScreen = "confirm",
                activeItemId = child.itemId
            )
        }
    }

    private fun MainTab.toIosTab(): String {
        return when (this) {
            MainTab.A -> "a"
            MainTab.B -> "b"
            MainTab.C -> "c"
        }
    }
}
