import SwiftUI
import shared

struct FeatureCDetailsView: View {
    let viewModel: IosFeatureCDetailsScreenViewModel

    var body: some View {
        VStack(spacing: 16) {
            Text(viewModel.title)
            Button("Go to confirm") {
                viewModel.onOpenConfirmClicked()
            }
            Button("Back") {
                viewModel.onBackClicked()
            }
        }
    }
}
