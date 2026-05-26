package com.example.testkmpdecomposeapp

import com.arkivanov.decompose.Cancellation
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.example.testkmpdecomposeapp.core.common.MainTab
import com.example.testkmpdecomposeapp.feature.a.api.FeatureAComponent
import com.example.testkmpdecomposeapp.feature.a.impl.FeatureADetailsViewModel
import com.example.testkmpdecomposeapp.feature.a.impl.FeatureAListViewModel
import com.example.testkmpdecomposeapp.feature.auth.impl.AuthViewModel
import com.example.testkmpdecomposeapp.feature.b.api.FeatureBComponent
import com.example.testkmpdecomposeapp.feature.b.impl.FeatureBDetailsViewModel
import com.example.testkmpdecomposeapp.feature.b.impl.FeatureBListViewModel
import com.example.testkmpdecomposeapp.feature.c.api.FeatureCComponent
import com.example.testkmpdecomposeapp.feature.c.impl.FeatureCConfirmViewModel
import com.example.testkmpdecomposeapp.feature.c.impl.FeatureCDetailsViewModel
import com.example.testkmpdecomposeapp.feature.c.impl.FeatureCListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
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

class IosAppBridge {
    private val lifecycle = LifecycleRegistry()
    private val root: AppRootComponent
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private var listener: IosAppStateListener? = null
    private var rootStackCancellation: Cancellation? = null
    private var mainStackCancellation: Cancellation? = null
    private var featureStackCancellation: Cancellation? = null
    private var observedMainComponent: MainComponent? = null
    private var observedFeatureOwner: Any? = null

    private var activeStateOwner: Any? = null
    private var activeStateJob: Job? = null

    private var authOwner: AppRootComponent.Child.Auth? = null
    private var authVm: AuthViewModel? = null

    private var featureAListOwner: FeatureAComponent? = null
    private var featureAListVm: FeatureAListViewModel? = null
    private var featureADetailsOwner: FeatureAComponent? = null
    private var featureADetailsItemId: Int? = null
    private var featureADetailsVm: FeatureADetailsViewModel? = null

    private var featureBListOwner: FeatureBComponent? = null
    private var featureBListVm: FeatureBListViewModel? = null
    private var featureBDetailsOwner: FeatureBComponent? = null
    private var featureBDetailsItemId: Int? = null
    private var featureBDetailsVm: FeatureBDetailsViewModel? = null

    private var featureCListOwner: FeatureCComponent? = null
    private var featureCListVm: FeatureCListViewModel? = null
    private var featureCDetailsOwner: FeatureCComponent? = null
    private var featureCDetailsItemId: Int? = null
    private var featureCDetailsVm: FeatureCDetailsViewModel? = null
    private var featureCConfirmOwner: FeatureCComponent? = null
    private var featureCConfirmItemId: Int? = null
    private var featureCConfirmVm: FeatureCConfirmViewModel? = null

    init {
        initKoin()
        root = AppRootComponent(DefaultComponentContext(lifecycle = lifecycle))
        observeNavigationStacks()
    }

    fun setListener(listener: IosAppStateListener?) {
        this.listener = listener
        notifyState()
    }

    fun close() {
        clearFeatureStackObservation()
        mainStackCancellation?.cancel()
        mainStackCancellation = null
        observedMainComponent = null
        rootStackCancellation?.cancel()
        rootStackCancellation = null

        activeStateJob?.cancel()
        activeStateJob = null
        activeStateOwner = null
        scope.cancel()
    }

    fun getState(): IosAppState = buildState()

    fun onSplashFinished() {
        val child = root.stack.value.active.instance as? AppRootComponent.Child.Splash ?: return
        child.onFinished()
        notifyState()
    }

    fun authViewModel(): AuthViewModel? {
        val child = root.stack.value.active.instance as? AppRootComponent.Child.Auth ?: run {
            clearStateObservation()
            return null
        }
        if (authOwner !== child || authVm == null) {
            authOwner = child
            authVm = getKoinInstance { parametersOf(child.onOutput) }
        }
        return authVm?.also { observeUiState(owner = child, flow = it.uiState) }
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

    fun featureAListViewModel(): FeatureAListViewModel? {
        val child = currentMainChild() as? MainComponent.Child.FeatureA ?: run {
            clearStateObservation()
            return null
        }
        val component = child.component
        if (component.stack.value.active.instance != FeatureAComponent.Child.List) {
            return null
        }

        if (featureAListOwner !== component || featureAListVm == null) {
            featureAListOwner = component
            featureAListVm = getKoinInstance { parametersOf(component::onOpenDetails) }
        }
        return featureAListVm?.also { observeUiState(owner = Pair(component, "a-list"), flow = it.uiState) }
    }

    fun featureADetailsViewModel(): FeatureADetailsViewModel? {
        val child = currentMainChild() as? MainComponent.Child.FeatureA ?: run {
            clearStateObservation()
            return null
        }
        val component = child.component
        val details = component.stack.value.active.instance as? FeatureAComponent.Child.Details ?: return null

        if (featureADetailsOwner !== component || featureADetailsItemId != details.itemId || featureADetailsVm == null) {
            featureADetailsOwner = component
            featureADetailsItemId = details.itemId
            featureADetailsVm = getKoinInstance {
                parametersOf(details.itemId, component::onBack, component::onOpenFeatureCConfirm)
            }
        }
        return featureADetailsVm?.also {
            observeUiState(owner = Triple(component, "a-details", details.itemId), flow = it.uiState)
        }
    }

    fun featureBListViewModel(): FeatureBListViewModel? {
        val child = currentMainChild() as? MainComponent.Child.FeatureB ?: run {
            clearStateObservation()
            return null
        }
        val component = child.component
        if (component.stack.value.active.instance != FeatureBComponent.Child.List) {
            return null
        }

        if (featureBListOwner !== component || featureBListVm == null) {
            featureBListOwner = component
            featureBListVm = getKoinInstance { parametersOf(component::onOpenDetails) }
        }
        return featureBListVm?.also { observeUiState(owner = Pair(component, "b-list"), flow = it.uiState) }
    }

    fun featureBDetailsViewModel(): FeatureBDetailsViewModel? {
        val child = currentMainChild() as? MainComponent.Child.FeatureB ?: run {
            clearStateObservation()
            return null
        }
        val component = child.component
        val details = component.stack.value.active.instance as? FeatureBComponent.Child.Details ?: return null

        if (featureBDetailsOwner !== component || featureBDetailsItemId != details.itemId || featureBDetailsVm == null) {
            featureBDetailsOwner = component
            featureBDetailsItemId = details.itemId
            featureBDetailsVm = getKoinInstance {
                parametersOf(details.itemId, component::onBack)
            }
        }
        return featureBDetailsVm?.also {
            observeUiState(owner = Triple(component, "b-details", details.itemId), flow = it.uiState)
        }
    }

    fun featureCListViewModel(): FeatureCListViewModel? {
        val child = currentMainChild() as? MainComponent.Child.FeatureC ?: run {
            clearStateObservation()
            return null
        }
        val component = child.component
        if (component.stack.value.active.instance != FeatureCComponent.Child.List) {
            return null
        }

        if (featureCListOwner !== component || featureCListVm == null) {
            featureCListOwner = component
            featureCListVm = getKoinInstance { parametersOf(component::onItemClick) }
        }
        return featureCListVm?.also { observeUiState(owner = Pair(component, "c-list"), flow = it.uiState) }
    }

    fun featureCDetailsViewModel(): FeatureCDetailsViewModel? {
        val child = currentMainChild() as? MainComponent.Child.FeatureC ?: run {
            clearStateObservation()
            return null
        }
        val component = child.component
        val details = component.stack.value.active.instance as? FeatureCComponent.Child.Details ?: return null

        if (featureCDetailsOwner !== component || featureCDetailsItemId != details.itemId || featureCDetailsVm == null) {
            featureCDetailsOwner = component
            featureCDetailsItemId = details.itemId
            featureCDetailsVm = getKoinInstance {
                parametersOf(details.itemId, component::onBack, component::onOpenConfirm)
            }
        }
        return featureCDetailsVm?.also {
            observeUiState(owner = Triple(component, "c-details", details.itemId), flow = it.uiState)
        }
    }

    fun featureCConfirmViewModel(): FeatureCConfirmViewModel? {
        val child = currentMainChild() as? MainComponent.Child.FeatureC ?: run {
            clearStateObservation()
            return null
        }
        val component = child.component
        val confirm = component.stack.value.active.instance as? FeatureCComponent.Child.Confirm ?: return null

        if (featureCConfirmOwner !== component || featureCConfirmItemId != confirm.itemId || featureCConfirmVm == null) {
            featureCConfirmOwner = component
            featureCConfirmItemId = confirm.itemId
            featureCConfirmVm = getKoinInstance {
                parametersOf(confirm.itemId, component::onBack, component::onDone)
            }
        }
        return featureCConfirmVm?.also {
            observeUiState(owner = Triple(component, "c-confirm", confirm.itemId), flow = it.uiState)
        }
    }

    private fun observeUiState(owner: Any, flow: StateFlow<*>) {
        if (activeStateOwner == owner) return

        activeStateJob?.cancel()
        activeStateOwner = owner
        activeStateJob = scope.launch {
            flow.collect {
                notifyState()
            }
        }
    }

    private fun clearStateObservation() {
        activeStateJob?.cancel()
        activeStateJob = null
        activeStateOwner = null
    }

    private fun observeNavigationStacks() {
        rootStackCancellation?.cancel()
        rootStackCancellation = root.stack.observe { stack ->
            val mainComponent = (stack.active.instance as? AppRootComponent.Child.Main)?.component
            observeMainStack(mainComponent)
            notifyState()
        }
    }

    private fun observeMainStack(mainComponent: MainComponent?) {
        if (observedMainComponent === mainComponent) return

        observedMainComponent = mainComponent
        mainStackCancellation?.cancel()
        mainStackCancellation = null
        clearFeatureStackObservation()

        if (mainComponent == null) return

        mainStackCancellation = mainComponent.stack.observe { stack ->
            observeFeatureStack(stack.active.instance)
            notifyState()
        }
    }

    private fun observeFeatureStack(mainChild: MainComponent.Child) {
        val (owner, stack) = when (mainChild) {
            is MainComponent.Child.FeatureA -> {
                mainChild.component to mainChild.component.stack
            }
            is MainComponent.Child.FeatureB -> {
                mainChild.component to mainChild.component.stack
            }
            is MainComponent.Child.FeatureC -> {
                mainChild.component to mainChild.component.stack
            }
        }

        if (observedFeatureOwner === owner) return

        clearFeatureStackObservation()
        observedFeatureOwner = owner
        featureStackCancellation = stack.observe {
            notifyState()
        }
    }

    private fun clearFeatureStackObservation() {
        featureStackCancellation?.cancel()
        featureStackCancellation = null
        observedFeatureOwner = null
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
