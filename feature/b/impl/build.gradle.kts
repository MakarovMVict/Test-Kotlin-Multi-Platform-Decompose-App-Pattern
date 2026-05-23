plugins {
    id("testkmp.kmp-module")
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":feature:b:api"))
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(libs.decompose)
            implementation(libs.decompose.compose)
            implementation(libs.koin.core)
        }
    }
}

android {
    namespace = "com.example.testkmpdecomposeapp.feature.b.impl"
}
