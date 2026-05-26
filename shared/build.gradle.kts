plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.kotlinCompose)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    androidTarget()

    val iosX64Target = iosX64()
    val iosArm64Target = iosArm64()
    val iosSimulatorArm64Target = iosSimulatorArm64()

    listOf(iosX64Target, iosArm64Target, iosSimulatorArm64Target).forEach { target ->
        target.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(project(":core:common"))
            implementation(project(":core:ui"))
            implementation(project(":feature:auth:api"))
            implementation(project(":feature:auth:impl"))
            implementation(project(":feature:a:api"))
            implementation(project(":feature:a:impl"))
            implementation(project(":feature:b:api"))
            implementation(project(":feature:b:impl"))
            implementation(project(":feature:c:api"))
            implementation(project(":feature:c:impl"))
            implementation(libs.koin.core)
            implementation(libs.decompose)
            implementation(libs.kotlinx.serialization.core)
        }
        androidMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(libs.decompose.compose)
        }
    }
}

android {
    namespace = "com.example.testkmpdecomposeapp.shared"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
}
