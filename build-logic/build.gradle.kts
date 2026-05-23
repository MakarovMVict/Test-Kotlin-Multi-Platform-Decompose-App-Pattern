plugins {
    `kotlin-dsl`
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

kotlin {
    jvmToolchain(17)
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    compileOnly("com.android.tools.build:gradle:8.5.2")
    compileOnly("org.jetbrains.kotlin:kotlin-gradle-plugin:2.0.20")
}

gradlePlugin {
    plugins {
        create("jvmTargetsConvention") {
            id = "testkmp.jvm-targets"
            implementationClass = "com.example.buildlogic.JvmTargetsConventionPlugin"
        }
        create("kmpModuleConvention") {
            id = "testkmp.kmp-module"
            implementationClass = "com.example.buildlogic.KmpModuleConventionPlugin"
        }
    }
}
