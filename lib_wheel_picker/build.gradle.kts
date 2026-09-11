plugins {
    alias(libs.plugins.android.library)
    //alias(libs.plugins.kotlin)
}
android {
    namespace = "com.aigestudio.wheelpicker"
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
    }
    buildTypes {
        register("preview") {
        }
    }
}

dependencies {
    implementation(libs.gson)
}
