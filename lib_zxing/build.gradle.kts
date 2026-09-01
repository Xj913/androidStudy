plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin)
}
android {
    namespace = "com.google.zxing"
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
    api(libs.zxing)
    implementation(project(":lib_common"))
}
