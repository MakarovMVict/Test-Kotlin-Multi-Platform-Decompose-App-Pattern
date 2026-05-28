import SwiftUI
import FeatureAUI
import FeatureBUI
import FeatureCUI

struct MainTabView: View {
    @ObservedObject var store: AppStore

    var body: some View {
        VStack(spacing: 0) {
            featureContent
                .frame(maxWidth: .infinity, maxHeight: .infinity)

            Divider()

            HStack {
                tabButton(title: "Feature A", tab: "a")
                tabButton(title: "Feature B", tab: "b")
                tabButton(title: "Feature C", tab: "c")
            }
            .padding(.vertical, 10)
        }
    }

    @ViewBuilder
    private var featureContent: some View {
        switch store.state.selectedTab {
        case "a":
            featureAContent
        case "b":
            featureBContent
        default:
            featureCContent
        }
    }

    @ViewBuilder
    private var featureAContent: some View {
        if let viewModel = store.featureAListViewModel() {
            FeatureAListView(viewModel: viewModel)
        } else if let viewModel = store.featureADetailsViewModel() {
            FeatureADetailsView(viewModel: viewModel)
        } else {
            Text("Feature A state unavailable")
        }
    }

    @ViewBuilder
    private var featureBContent: some View {
        if let viewModel = store.featureBListViewModel() {
            FeatureBListView(viewModel: viewModel)
        } else if let viewModel = store.featureBDetailsViewModel() {
            FeatureBDetailsView(viewModel: viewModel)
        } else {
            Text("Feature B state unavailable")
        }
    }

    @ViewBuilder
    private var featureCContent: some View {
        if let viewModel = store.featureCListViewModel() {
            FeatureCListView(viewModel: viewModel)
        } else if let viewModel = store.featureCDetailsViewModel() {
            FeatureCDetailsView(viewModel: viewModel)
        } else if let viewModel = store.featureCConfirmViewModel() {
            FeatureCConfirmView(viewModel: viewModel)
        } else {
            Text("Feature C state unavailable")
        }
    }

    private func tabButton(title: String, tab: String) -> some View {
        Button(action: { store.selectTab(tab) }) {
            Text(title)
                .frame(maxWidth: .infinity)
        }
        .buttonStyle(.borderless)
    }
}
