plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin)
}
android {
    namespace = "com.dmcbig.mediapicker"
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
    implementation(libs.appcompat)
    implementation(libs.recyclerview)
    implementation(libs.bundles.glide, {
        exclude(group = "com.squareup.okhttp", module = "okhttp")
    })
}