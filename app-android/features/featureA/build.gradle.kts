plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.kotlinCompose)
}

android {
    namespace = "com.example.testkmpdecomposeapp.android.features.featurea.module"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(":feature:a:api"))
    implementation(project(":feature:a:impl"))
    implementation(libs.decompose)
    implementation(compose.runtime)
    implementation(compose.foundation)
    implementation(compose.material3)
    implementation(libs.decompose.compose)
    implementation(libs.koin.core)
}
