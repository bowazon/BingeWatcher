pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "BingeWatcher"
include(":app")
include(":core:designsystem")
include(":core:network")
include(":core:navigation")
include(":features:discover:api")
include(":features:discover:impl")
include(":features:search:api")
include(":features:search:impl")
include(":core:entities")
include(":core:repository")
include(":features:show:api")
include(":features:show:impl")
include(":features:person:api")
include(":features:person:impl")
include(":features:season:api")
include(":features:season:impl")
include(":features:imageview:api")
include(":features:imageview:impl")
