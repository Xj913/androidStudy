plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin)
}
android {
    namespace = "com.dmcbig.mediapicker"
    compileSdk = (findProperty("compileSdk") as String).toInt()
    defaultConfig {
        minSdk = (findProperty("minSdk") as String).toInt()
    }
    buildTypes {
        register("preview") {
        }
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.recyclerview)
    implementation(libs.glide)
    implementation("com.github.bumptech.glide:okhttp3-integration:4.9", {
        exclude(group = "com.squareup.okhttp", module = "okhttp")
    })
}