plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.kotlin.compose) apply false
}

allprojects {

    configurations.configureEach {
        resolutionStrategy {
            /*force 'androidx.annotation:annotation-experimental:1.1.0'
            force 'androidx.lifecycle:lifecycle-livedata:2.5.1'
            force 'androidx.coordinatorlayout:coordinatorlayout:1.2.0'
            force 'androidx.arch.core:core-runtime:2.2.0'
            force 'androidx.lifecycle:lifecycle-service:2.8.7'
            force 'androidx.lifecycle:lifecycle-viewmodel-savedstate:2.8.7'
            */
        }
    }
}
tasks.register("clean", Delete::class) {
    description = ""
    delete(rootProject.buildDir)
}

