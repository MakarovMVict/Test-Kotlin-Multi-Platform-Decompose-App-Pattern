import SwiftUI
import shared

public struct FeatureAContainerView: View {
    public let listViewModelProvider: () -> FeatureAListViewModel?
    public let detailsViewModelProvider: () -> FeatureADetailsViewModel?

    public init(
        listViewModelProvider: @escaping () -> FeatureAListViewModel?,
        detailsViewModelProvider: @escaping () -> FeatureADetailsViewModel?
    ) {
        self.listViewModelProvider = listViewModelProvider
        self.detailsViewModelProvider = detailsViewModelProvider
    }

    public var body: some View {
        if let viewModel = listViewModelProvider() {
            FeatureAListView(viewModel: viewModel)
        } else if let viewModel = detailsViewModelProvider() {
            FeatureADetailsView(viewModel: viewModel)
        } else {
            Text("Feature A state unavailable")
        }
    }
}
