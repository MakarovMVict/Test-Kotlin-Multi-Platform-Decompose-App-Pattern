import SwiftUI
import Foundation
import shared

final class AppStore: ObservableObject {
    @Published var state: IosAppState

    private let bridge: IosAppBridge
    private lazy var listener = AppStateListener { [weak self] state in
        DispatchQueue.main.async {
            self?.state = state
        }
    }

    init() {
        let bridge = IosAppBridge()
        self.bridge = bridge
        self.state = bridge.getState()
        bridge.setListener(listener: listener)
    }

    deinit {
        bridge.setListener(listener: nil)
        bridge.close()
    }

    func onSplashFinished() {
        bridge.onSplashFinished()
    }

    func authViewModel() -> AuthViewModel? {
        bridge.authViewModel()
    }

    func selectTab(_ tab: String) {
        switch tab {
        case "a":
            bridge.selectTabA()
        case "b":
            bridge.selectTabB()
        case "c":
            bridge.selectTabC()
        default:
            break
        }
    }

    func featureAListViewModel() -> FeatureAListViewModel? {
        bridge.featureAListViewModel()
    }

    func featureADetailsViewModel() -> FeatureADetailsViewModel? {
        bridge.featureADetailsViewModel()
    }

    func featureBListViewModel() -> FeatureBListViewModel? {
        bridge.featureBListViewModel()
    }

    func featureBDetailsViewModel() -> FeatureBDetailsViewModel? {
        bridge.featureBDetailsViewModel()
    }

    func featureCListViewModel() -> FeatureCListViewModel? {
        bridge.featureCListViewModel()
    }

    func featureCDetailsViewModel() -> FeatureCDetailsViewModel? {
        bridge.featureCDetailsViewModel()
    }

    func featureCConfirmViewModel() -> FeatureCConfirmViewModel? {
        bridge.featureCConfirmViewModel()
    }
}

private final class AppStateListener: NSObject, IosAppStateListener {
    private let onStateChangedCallback: (IosAppState) -> Void

    init(onStateChangedCallback: @escaping (IosAppState) -> Void) {
        self.onStateChangedCallback = onStateChangedCallback
    }

    func onStateChanged(state: IosAppState) {
        onStateChangedCallback(state)
    }
}

struct RootView: View {
    @StateObject private var store = AppStore()

    var body: some View {
        switch store.state.root {
        case "splash":
            SplashView(onFinished: store.onSplashFinished)
        case "auth":
            if let viewModel = store.authViewModel() {
                AuthView(viewModel: viewModel)
            } else {
                Text("Auth view model unavailable")
            }
        case "main":
            MainTabView(store: store)
        default:
            Text("Unknown state")
        }
    }
}
