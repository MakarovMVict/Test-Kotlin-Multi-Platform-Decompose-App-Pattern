plugins {
    id("testkmp.kmp-module")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":feature:auth:api"))
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(libs.koin.core)
        }
    }
}

android {
    namespace = "com.example.testkmpdecomposeapp.feature.auth.impl"
}
