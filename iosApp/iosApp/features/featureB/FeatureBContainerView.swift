import SwiftUI
import shared

public struct FeatureBContainerView: View {
    public let listViewModelProvider: () -> FeatureBListViewModel?
    public let detailsViewModelProvider: () -> FeatureBDetailsViewModel?

    public init(
        listViewModelProvider: @escaping () -> FeatureBListViewModel?,
        detailsViewModelProvider: @escaping () -> FeatureBDetailsViewModel?
    ) {
        self.listViewModelProvider = listViewModelProvider
        self.detailsViewModelProvider = detailsViewModelProvider
    }

    public var body: some View {
        if let viewModel = listViewModelProvider() {
            FeatureBListView(viewModel: viewModel)
        } else if let viewModel = detailsViewModelProvider() {
            FeatureBDetailsView(viewModel: viewModel)
        } else {
            Text("Feature B state unavailable")
        }
    }
}
