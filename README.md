# TestKMPDecomposeApp

Kotlin Multiplatform project scaffold with:

- Android UI on Compose (`app-android` + shared composables)
- iOS host app on SwiftUI + shared Compose UI (`iosApp`)
- DI via Koin
- Navigation via Decompose in shared layer
- Multi-module clean architecture layout:
  - `shared` as app-level orchestration module
  - `core/*` base modules
  - `feature/*/(api|impl)` for features

## Features included

- Splash screen
- Auth feature (login screen)
- Main screen with bottom tab bar and 3 features: A, B, C

## Modules

- `app-android` - Android entry point
- `shared` - root app flow and wiring
- `core:common`, `core:ui`
- `feature:auth:api`, `feature:auth:impl`
- `feature:a:api`, `feature:a:impl`
- `feature:b:api`, `feature:b:impl`
- `feature:c:api`, `feature:c:impl`
- `iosApp` - SwiftUI app shell

## Run

### Android

1. Open the **repository root** (`TestKMPDecomposeApp`) in Android Studio.
2. After Gradle sync, choose run configuration **`app-android`** and run on an emulator or device.

Android Studio only shows Android run configurations — it does **not** build or run the iOS app.

### iOS

The iOS host app is a separate **Xcode** project (not Gradle):

1. Open **`iosApp/TestKMPDecomposeApp.xcodeproj`** in Xcode (double-click or **File → Open**).
2. Select a simulator or device in the toolbar, then **Run** (⌘R).

From the repo root, build the shared Kotlin framework for the simulator:

```bash
./gradlew :shared:linkDebugFrameworkIosSimulatorArm64
```
