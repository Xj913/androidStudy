plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.kotlin.compose) apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven {
            setUrl("https://jitpack.io")
        }
        maven {
            setUrl("https://maven.aliyun.com/nexus/content/repositories/releases")
        }
    }
    configurations.configureEach {
        resolutionStrategy {
            force(libs.ktx.coroutines.android)
            force(libs.coordinatorlayout)
            force(libs.constraintlayout)
            force(libs.okio)
            force(libs.org.annotations)
        }
    }
}

tasks.register<Delete>("clean") {
    description = ""
    delete(rootProject.layout.buildDirectory)
}
