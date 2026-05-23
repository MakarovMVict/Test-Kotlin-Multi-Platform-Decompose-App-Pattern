package com.example.buildlogic

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KmpModuleConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("org.jetbrains.kotlin.multiplatform")
            pluginManager.apply("com.android.library")
            pluginManager.apply("org.jetbrains.compose")
            pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

            extensions.configure(KotlinMultiplatformExtension::class.java) {
                androidTarget()
                iosX64()
                iosArm64()
                iosSimulatorArm64()
            }

            extensions.configure(LibraryExtension::class.java) {
                compileSdk = 34
                defaultConfig {
                    minSdk = 24
                }
            }
        }
    }
}
