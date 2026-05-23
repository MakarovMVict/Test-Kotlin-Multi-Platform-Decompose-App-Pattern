import SwiftUI

struct AuthView: View {
    let onLoginSuccess: () -> Void

    var body: some View {
        VStack(spacing: 16) {
            Text("Экран авторизации")
            Button("Войти") {
                onLoginSuccess()
            }
        }
    }
}
