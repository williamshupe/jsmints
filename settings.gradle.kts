pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven ("https://dl.bintray.com/kotlin/kotlin-eap")
        maven ("https://kotlin.bintray.com/kotlinx")

    }
}

rootProject.name = "testmints"
include("minassert")
include("standard")
include("async")
include("async-js")
include("minspy")
include("mindiff")
