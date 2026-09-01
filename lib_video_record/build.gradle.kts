plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin)
}
android {
    namespace = "com.style.lib.media.video"
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
}