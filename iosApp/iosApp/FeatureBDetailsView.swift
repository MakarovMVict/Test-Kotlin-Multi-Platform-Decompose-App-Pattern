import SwiftUI
import shared

struct FeatureBDetailsView: View {
    let viewModel: IosFeatureBDetailsScreenViewModel

    var body: some View {
        VStack(spacing: 16) {
            Text(viewModel.title)
            Button("Back") {
                viewModel.onBackClicked()
            }
        }
    }
}
