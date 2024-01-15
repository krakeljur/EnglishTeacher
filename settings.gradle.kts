pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "EnglishTeacher"
include(":app")
include(":features:sign-up")
include(":features:sign-in")
include(":features:profile")
include(":features:favorites")
include(":features:catalog")
include(":features:game")
include(":features:peoples")
include(":data")
