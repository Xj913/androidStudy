plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.xiajun.lib.common"
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.compose.ui.test.junit)
    debugImplementation(libs.compose.ui.test.manifest)
    implementation(libs.kotlin)
    implementation(platform(libs.compose.bom))
    //androidTestImplementation(libs.compose.bom)
    debugImplementation(libs.ui.tool)
    debugImplementation(libs.ui.preview)
    implementation(libs.bundles.base)
    implementation(libs.bundles.compose)
    api(libs.room)
    ksp(libs.room.compiler)
    api(libs.fastjson)
    api(libs.bundles.retrofit)
    api(libs.refresh.layout)
    api(projects.libCustomView)
}

