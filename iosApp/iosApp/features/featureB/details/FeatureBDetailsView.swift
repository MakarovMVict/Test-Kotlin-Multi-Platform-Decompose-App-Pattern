import SwiftUI
import shared

struct FeatureBDetailsView: View {
    let viewModel: FeatureBDetailsViewModel

    var body: some View {
        VStack(spacing: 16) {
            Text(viewModel.state.title)
            Button("Back") {
                viewModel.onBackClicked()
            }
        }
    }
}
