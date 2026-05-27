import SwiftUI

@main
struct TestKMPDecomposeApp: App {
    @UIApplicationDelegateAdaptor(NotificationAppDelegate.self) private var appDelegate

    var body: some Scene {
        WindowGroup {
            RootView()
//                .ignoresSafeArea()
        }
    }
}
