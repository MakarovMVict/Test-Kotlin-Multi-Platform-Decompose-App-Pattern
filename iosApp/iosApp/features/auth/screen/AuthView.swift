import SwiftUI
import shared

struct AuthView: View {
    let viewModel: AuthViewModel

    var body: some View {
        VStack(spacing: 16) {
            Text(viewModel.state.title)
            Button(viewModel.state.loginButtonTitle) {
                viewModel.onLoginClicked()
            }
        }
    }
}
