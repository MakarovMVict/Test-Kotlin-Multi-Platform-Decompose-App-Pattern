plugins {
    id("testkmp.kmp-module")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.material3)
        }
    }
}

android {
    namespace = "com.example.testkmpdecomposeapp.core.ui"
}
