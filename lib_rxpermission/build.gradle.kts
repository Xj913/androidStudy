plugins {
    alias(libs.plugins.android.library)
}
android {
    namespace = "com.lib.permission"
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
    implementation(libs.fragment)
    implementation(libs.rxjava)
    implementation(libs.and.annotation)
}
