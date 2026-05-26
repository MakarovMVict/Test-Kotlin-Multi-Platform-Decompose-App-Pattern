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
            export(project(":core:common"))
            export(project(":feature:auth:api"))
            export(project(":feature:auth:impl"))
            export(project(":feature:a:api"))
            export(project(":feature:a:impl"))
            export(project(":feature:b:api"))
            export(project(":feature:b:impl"))
            export(project(":feature:c:api"))
            export(project(":feature:c:impl"))
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(project(":core:common"))
            implementation(project(":core:ui"))
            implementation(compose.runtime)
            api(project(":feature:auth:api"))
            api(project(":feature:auth:impl"))
            api(project(":feature:a:api"))
            api(project(":feature:a:impl"))
            api(project(":feature:b:api"))
            api(project(":feature:b:impl"))
            api(project(":feature:c:api"))
            api(project(":feature:c:impl"))
            implementation(libs.koin.core)
            implementation(libs.decompose)
            implementation(libs.kotlinx.serialization.core)
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
