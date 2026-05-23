package com.example.buildlogic

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

class JvmTargetsConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            plugins.withId("com.android.application") {
                extensions.configure(ApplicationExtension::class.java) {
                    compileOptions {
                        sourceCompatibility = JavaVersion.VERSION_17
                        targetCompatibility = JavaVersion.VERSION_17
                    }
                }
            }

            plugins.withId("com.android.library") {
                extensions.configure(LibraryExtension::class.java) {
                    compileOptions {
                        sourceCompatibility = JavaVersion.VERSION_17
                        targetCompatibility = JavaVersion.VERSION_17
                    }
                }
            }

            plugins.withId("org.jetbrains.kotlin.multiplatform") {
                extensions.configure(KotlinMultiplatformExtension::class.java) {
                    jvmToolchain(17)
                }
            }

            plugins.withId("org.jetbrains.kotlin.android") {
                extensions.configure(KotlinAndroidProjectExtension::class.java) {
                    jvmToolchain(17)
                }
            }

            tasks.withType(KotlinJvmCompile::class.java).configureEach {
                compilerOptions.jvmTarget.set(JvmTarget.JVM_17)
            }
        }
    }
}
