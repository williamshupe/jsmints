
import org.jetbrains.kotlin.gradle.targets.js.ir.KotlinJsIrLink
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension

plugins {
    id("com.zegreatrob.jsmints.plugins.js2")
    id("com.zegreatrob.jsmints.plugins.lint")
    id("com.zegreatrob.jsmints.plugins.publish")
}

kotlin {
    js {
        nodejs {
            useCommonJs()
        }
        binaries.executable()
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(platform(libs.org.jetbrains.kotlin.kotlin.bom))
    implementation(platform(libs.org.jetbrains.kotlinx.kotlinx.coroutines.bom))
    implementation(platform(libs.org.jetbrains.kotlin.wrappers.kotlin.wrappers.bom))
    implementation(libs.io.github.microutils.kotlin.logging)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-js")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-extensions")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-node")
    implementation(jsconstraint("@wdio/cli"))
    implementation(jsconstraint("@wdio/dot-reporter"))
    implementation(jsconstraint("@wdio/junit-reporter"))
    implementation(jsconstraint("@wdio/local-runner"))
    implementation(jsconstraint("@wdio/mocha-framework"))
}

val executable: Configuration by configurations.creating

group = "com.zegreatrob.jsmints"

val compileExecutableTask = tasks.named("compileProductionExecutableKotlinJs", KotlinJsIrLink::class)

tasks {
    val executableJar by registering(Jar::class) {
        dependsOn(compileExecutableTask)
        archiveClassifier.set("executable")
        from(compileExecutableTask.map { it.outputFileProperty.get().parentFile })
    }

    artifacts.add(executable.name, executableJar) {
        classifier = executable.name
        builtBy(executableJar)
    }

    publishing.publications {
        withType<MavenPublication> { artifact(executableJar) }
    }
}

rootProject.extensions.findByType(NodeJsRootExtension::class.java).let {
    it?.nodeVersion = "19.6.0"
}

publishing {
    publications {
        create<MavenPublication>("js") {
            from(components["kotlin"])
        }
    }
}
