pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    includeBuild("build-logic")
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "TestKMPDecomposeApp"

include(":app-android")
include(":app-android:features:authFeature")
include(":app-android:features:featureA")
include(":app-android:features:featureB")
include(":app-android:features:featureC")
include(":shared")

include(":core:common")
include(":core:ui")

include(":feature:auth:api")
include(":feature:auth:impl")
include(":feature:a:api")
include(":feature:a:impl")
include(":feature:b:api")
include(":feature:b:impl")
include(":feature:c:api")
include(":feature:c:impl")
