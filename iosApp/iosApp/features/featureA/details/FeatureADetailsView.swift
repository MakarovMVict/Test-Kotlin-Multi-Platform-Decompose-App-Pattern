import SwiftUI
import shared

public struct FeatureADetailsView: View {
    public let viewModel: FeatureADetailsViewModel

    public init(viewModel: FeatureADetailsViewModel) {
        self.viewModel = viewModel
    }

    public var body: some View {
        VStack(spacing: 16) {
            Text(viewModel.state.title)
            Button("Open Feature C confirm") {
                viewModel.onOpenFeatureCConfirmClicked()
            }
            Button("Back") {
                viewModel.onBackClicked()
            }
        }
    }
}
