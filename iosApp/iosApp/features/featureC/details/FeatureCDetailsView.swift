import SwiftUI
import shared

struct FeatureCDetailsView: View {
    let viewModel: FeatureCDetailsViewModel

    var body: some View {
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
