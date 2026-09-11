plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    //alias(libs.plugins.kotlin) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.compose.compiler) apply false
}

allprojects {
    configurations.configureEach {
        resolutionStrategy {
            force(libs.lifecycle.viewmodel)
            force(libs.ktx.coroutines.android)
            force(libs.coordinatorlayout)
            force(libs.constraintlayout)
            force(libs.okio)
            force(libs.org.annotations)
        }
    }
}

tasks.register<Delete>("clean") {
    description = ""
    delete(rootProject.layout.buildDirectory)
}
