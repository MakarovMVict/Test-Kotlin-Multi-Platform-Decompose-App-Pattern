import SwiftUI

struct MainTabView: View {
    var body: some View {
        TabView {
            Text("Feature A")
                .tabItem {
                    Label("A", systemImage: "a.circle")
                }

            Text("Feature B")
                .tabItem {
                    Label("B", systemImage: "b.circle")
                }

            Text("Feature C")
                .tabItem {
                    Label("C", systemImage: "c.circle")
                }
        }
    }
}
