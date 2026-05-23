plugins {
    id("testkmp.kmp-module")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(libs.decompose)
        }
    }
}

android {
    namespace = "com.example.testkmpdecomposeapp.feature.b.api"
}
