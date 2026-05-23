import SwiftUI

struct SplashView: View {
    let onFinished: () -> Void

    var body: some View {
        Text("Splash")
            .task {
                try? await Task.sleep(nanoseconds: 1_200_000_000)
                onFinished()
            }
    }
}
