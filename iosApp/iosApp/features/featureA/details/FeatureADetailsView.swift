import SwiftUI
import shared

struct FeatureADetailsView: View {
    let viewModel: FeatureADetailsViewModel

    var body: some View {
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
