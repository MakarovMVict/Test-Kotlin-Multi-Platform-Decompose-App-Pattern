import SwiftUI
import shared

public struct FeatureCContainerView: View {
    public let listViewModelProvider: () -> FeatureCListViewModel?
    public let detailsViewModelProvider: () -> FeatureCDetailsViewModel?
    public let confirmViewModelProvider: () -> FeatureCConfirmViewModel?

    public init(
        listViewModelProvider: @escaping () -> FeatureCListViewModel?,
        detailsViewModelProvider: @escaping () -> FeatureCDetailsViewModel?,
        confirmViewModelProvider: @escaping () -> FeatureCConfirmViewModel?
    ) {
        self.listViewModelProvider = listViewModelProvider
        self.detailsViewModelProvider = detailsViewModelProvider
        self.confirmViewModelProvider = confirmViewModelProvider
    }

    public var body: some View {
        if let viewModel = listViewModelProvider() {
            FeatureCListView(viewModel: viewModel)
        } else if let viewModel = detailsViewModelProvider() {
            FeatureCDetailsView(viewModel: viewModel)
        } else if let viewModel = confirmViewModelProvider() {
            FeatureCConfirmView(viewModel: viewModel)
        } else {
            Text("Feature C state unavailable")
        }
    }
}
