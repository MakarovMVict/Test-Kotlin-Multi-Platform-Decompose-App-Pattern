import SwiftUI
import shared

struct AuthView: View {
    let viewModel: IosAuthScreenViewModel

    var body: some View {
        VStack(spacing: 16) {
            Text(viewModel.title)
            Button(viewModel.loginButtonTitle) {
                viewModel.onLoginClicked()
            }
        }
    }
}
