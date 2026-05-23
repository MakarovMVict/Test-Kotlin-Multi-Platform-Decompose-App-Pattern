plugins {
    id("testkmp.kmp-module")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
        }
    }
}

android {
    namespace = "com.example.testkmpdecomposeapp.feature.auth.api"
}
