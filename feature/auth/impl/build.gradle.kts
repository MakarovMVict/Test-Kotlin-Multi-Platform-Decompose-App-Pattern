plugins {
    id("testkmp.kmp-module")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":feature:auth:api"))
            implementation(compose.runtime)
            implementation(libs.koin.core)
        }
    }
}

android {
    namespace = "com.example.testkmpdecomposeapp.feature.auth.impl"
}
