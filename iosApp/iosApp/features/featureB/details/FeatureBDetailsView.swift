import SwiftUI
import shared

public struct FeatureBDetailsView: View {
    public let viewModel: FeatureBDetailsViewModel

    public init(viewModel: FeatureBDetailsViewModel) {
        self.viewModel = viewModel
    }

    public var body: some View {
        VStack(spacing: 16) {
            Text(viewModel.state.title)
            Button("Back") {
                viewModel.onBackClicked()
            }
        }
    }
}
