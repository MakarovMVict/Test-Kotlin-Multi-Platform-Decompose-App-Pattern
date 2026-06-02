plugins {
    id("testkmp.kmp-module")
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":feature:a:api"))
            implementation(compose.runtime)
            implementation(libs.decompose)
            implementation(libs.koin.core)
        }
    }
}

android {
    namespace = "com.example.testkmpdecomposeapp.feature.a.impl"
}
