plugins {
    id("com.android.application") version "8.5.2" apply false
    id("com.android.library") version "8.5.2" apply false
    kotlin("android") version "2.0.20" apply false
    kotlin("multiplatform") version "2.0.20" apply false
    kotlin("plugin.compose") version "2.0.20" apply false
    id("org.jetbrains.compose") version "1.7.3" apply false
    id("testkmp.jvm-targets") apply false
    id("testkmp.kmp-module") apply false
}

subprojects {
    apply(plugin = "testkmp.jvm-targets")
}
