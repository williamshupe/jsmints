import com.zegreatrob.testmints.build.BuildConstants
import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile

plugins {
    id("org.jetbrains.kotlin.multiplatform") version "1.4-M1"
}

repositories {
    mavenCentral()
    maven ("https://dl.bintray.com/kotlin/kotlin-eap")
    maven ("https://kotlin.bintray.com/kotlinx")
}

kotlin {
    targets {
        jvm()
        js {
            //            nodejs {}
        }
        macosX64()
        linuxX64()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-stdlib-common:${BuildConstants.kotlinVersion}")
                implementation("org.jetbrains.kotlin:kotlin-test-common:${BuildConstants.kotlinVersion}")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(project(":standard"))
                implementation(project(":minassert"))
                implementation("org.jetbrains.kotlin:kotlin-test-annotations-common:${BuildConstants.kotlinVersion}")
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-test-junit:${BuildConstants.kotlinVersion}")
            }
        }

        val nativeCommonMain by creating {
            dependsOn(commonMain)
        }

        val macosX64Main by getting { dependsOn(nativeCommonMain) }

        val linuxX64Main by getting { dependsOn(nativeCommonMain) }

        val jsMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-stdlib-js:${BuildConstants.kotlinVersion}")
                implementation("org.jetbrains.kotlin:kotlin-test-js")
            }
        }
    }
}

tasks {
    getByName<Kotlin2JsCompile>("compileKotlinJs") {
        kotlinOptions.moduleKind = "umd"
        kotlinOptions.sourceMap = true
        kotlinOptions.sourceMapEmbedSources = "always"
    }
}



