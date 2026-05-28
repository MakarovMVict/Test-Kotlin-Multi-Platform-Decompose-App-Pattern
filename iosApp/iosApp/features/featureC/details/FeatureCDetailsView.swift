import SwiftUI
import shared

public struct FeatureCDetailsView: View {
    public let viewModel: FeatureCDetailsViewModel

    public init(viewModel: FeatureCDetailsViewModel) {
        self.viewModel = viewModel
    }

    public var body: some View {
        VStack(spacing: 16) {
            Text(viewModel.state.title)
            Button("Go to confirm") {
                viewModel.onOpenConfirmClicked()
            }
            Button("Back") {
                viewModel.onBackClicked()
            }
        }
    }
}
