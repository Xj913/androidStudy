plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.style.lib.common"
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        ndk {
            //moduleName "helloNDK"
            abiFilters += listOf("armeabi-v7a", "arm64-v8a")
        }
    }
    /*externalNativeBuild {
        cmake {
            path 'CMakeLists.txt'
            //cppFlags "-frtti -fexceptions"
        }
    }*/
    buildTypes {
        register("preview") {
        }
    }
    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        compilerOptions {
            languageVersion = org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_2
        }
    }

}

dependencies {
    implementation(libs.kotlin)
    api(libs.room)
    ksp(libs.room.compiler)
    implementation(libs.appcompat)
    implementation(libs.recyclerview)
    implementation(libs.ktx.coroutines.android)
    api(libs.fastjson)
    api(libs.gson)
    api(libs.bundles.retrofit)
    api(libs.refresh.layout)
    api(libs.live.event.bus)
    api(projects.libCustomView)
}

