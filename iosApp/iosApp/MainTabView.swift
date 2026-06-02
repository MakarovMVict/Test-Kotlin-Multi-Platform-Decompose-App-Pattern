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
        FeatureAContainerView(
            listViewModelProvider: store.featureAListViewModel,
            detailsViewModelProvider: store.featureADetailsViewModel
        )
    }

    @ViewBuilder
    private var featureBContent: some View {
        FeatureBContainerView(
            listViewModelProvider: store.featureBListViewModel,
            detailsViewModelProvider: store.featureBDetailsViewModel
        )
    }

    @ViewBuilder
    private var featureCContent: some View {
        FeatureCContainerView(
            listViewModelProvider: store.featureCListViewModel,
            detailsViewModelProvider: store.featureCDetailsViewModel,
            confirmViewModelProvider: store.featureCConfirmViewModel
        )
    }

    private func tabButton(title: String, tab: String) -> some View {
        Button(action: { store.selectTab(tab) }) {
            Text(title)
                .frame(maxWidth: .infinity)
        }
        .buttonStyle(.borderless)
    }
}
