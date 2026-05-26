plugins {
    id("testkmp.kmp-module")
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":feature:a:api"))
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(libs.decompose)
            implementation(libs.koin.core)
        }
        androidMain.dependencies {
            implementation(libs.decompose.compose)
        }
    }
}

android {
    namespace = "com.example.testkmpdecomposeapp.feature.a.impl"
}
