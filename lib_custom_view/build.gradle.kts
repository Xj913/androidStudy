import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin)
}

android {
    namespace = "com.style.lib_custom_view"
    compileSdk = (findProperty("compileSdk") as String).toInt()
    ndkVersion = findProperty("ndk") as String
    defaultConfig {
        minSdk = (findProperty("minSdk") as String).toInt()
    }
    buildTypes {
        register("preview") {
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }

}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.recyclerview)
}
