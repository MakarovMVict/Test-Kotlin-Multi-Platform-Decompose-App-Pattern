plugins {
    id("testkmp.kmp-module")
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":feature:c:api"))
            implementation(compose.runtime)
            implementation(libs.decompose)
            implementation(libs.koin.core)
        }
        androidMain.dependencies {
            implementation(libs.androidx.core.ktx)
        }
    }
}

android {
    namespace = "com.example.testkmpdecomposeapp.feature.c.impl"
}
