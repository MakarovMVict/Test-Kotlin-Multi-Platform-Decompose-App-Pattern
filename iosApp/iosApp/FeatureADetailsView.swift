import SwiftUI
import shared

struct FeatureADetailsView: View {
    let viewModel: IosFeatureADetailsScreenViewModel

    var body: some View {
        VStack(spacing: 16) {
            Text(viewModel.title)
            Button("Open Feature C confirm") {
                viewModel.onOpenFeatureCConfirmClicked()
            }
            Button("Back") {
                viewModel.onBackClicked()
            }
        }
    }
}
