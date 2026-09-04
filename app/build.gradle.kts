import com.android.build.gradle.internal.api.ApkVariantOutputImpl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Date
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Properties
import java.io.FileInputStream

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.compose)
}
val releaseTime: String = SimpleDateFormat("yyyy-MM-dd_HH_mm_ss", Locale.getDefault()).format(Date())
val properties = Properties()
properties.load(FileInputStream(project.rootProject.file("local.properties")))

android {
    namespace = "com.style.app.MyApp"
    compileSdk = libs.versions.compileSdk.get().toInt()
    ndkVersion = libs.versions.ndk.get()
    defaultConfig {
        applicationId = "com.style.framework"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 9
        versionName = "2.9.0"
        multiDexEnabled = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        ndk {
            //moduleName "helloNDK"
            abiFilters += listOf("arm64-v8a", "armeabi-v7a")
        }
    }
    //-frtti支持 RTTI,,-fexceptions启用对 C++ 异常处理的支持
    //菜单build下make app会生成so文件，app/build/intermediates/cmake
    externalNativeBuild {
        cmake {
            path = file("CMakeLists.txt")
            //cppFlags "-frtti -fexceptions"
        }
    }
    flavorDimensions += listOf("model", "channel")
    productFlavors {
        create("dev") {
            dimension = "model"
            applicationIdSuffix = ".dev"
            buildConfigField("boolean", "isDebug", "true")
            resValue("string", "app_name", "App Dev")
        }
        create("wdj"){
            dimension = "channel"
            manifestPlaceholders["CHANNEL"] = "豌豆荚"
        }
    }
    applicationVariants.all {
        val buildType = buildType.name
        outputs.all {
            if (this is ApkVariantOutputImpl) {
                if (buildType == "release") {
                    outputFileName = "DEMO_v${versionCode}_${flavorName}_${releaseTime}.apk"
                }
            }
        }
    }

//    applicationVariants.all { variant ->
//        val buildType = variant.buildType.name
//        variant.outputs.all { output ->
//            if (output is com.android.build.gradle.internal.api.ApkVariantOutputImpl) {
//                if (buildType == "release") {
//                    output.outputFileName =
//                        "${variant.flavorName}_v${variant.versionName}_${variant.buildType.name}_${releaseTime}.apk"
//                }
//            }
//        }
//    }
    signingConfigs {
        getByName("debug") {
            //storeFile file(properties.getProperty("STORE_FILE_DEBUG"))
            /*storePassword properties.getProperty("STORE_PASSWORD")
              keyAlias project.KEY_ALIAS
              keyPassword project.KEY_PASSWORD*/
            //v2SigningEnabled false
        }
        create("release") {
            enableV1Signing = true
            enableV2Signing = true
            keyAlias = "keyname"
            keyPassword = "dmc19910809"
            storeFile = file("D:/AndroidStudioProjects/androidStudy/app/signingConfig/release.jks")
            storePassword = "dmc19910809"
        }
    }
    buildTypes {
        getByName("debug") {
            buildConfigField("boolean", "LOG_ENABLE", "true")
            isDebuggable = true
            isJniDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("debug")
        }
        /*register("preview") {
            buildConfigField("boolean", "LOG_ENABLE", "true")
            debuggable true
            jniDebuggable true
            minifyEnabled false
            zipAlignEnabled false
            shrinkResources false
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
            signingConfig signingConfigs.debug
        }
        getByName("release") {
            buildConfigField("boolean", "LOG_ENABLE", "false")
            debuggable false
            jniDebuggable false
            minifyEnabled true
            zipAlignEnabled true
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
            signingConfig signingConfigs.release
        }*/
    }
    lint {
        // true--所有正式版构建执行规则生成崩溃的lint检查，如果有崩溃问题停止构建
        checkReleaseBuilds = false
        // true--错误发生后停止gradle构建
        abortOnError = false
    }
    buildFeatures {
        buildConfig = true
        dataBinding = true
        viewBinding = true
        compose = true
        // flavorDimensions = listof("model", "channel")
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.5"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }
}

dependencies {
    testImplementation(libs.junit)
    implementation(platform(libs.kotlin.bom))
    //implementation(libs.kotlin)
    //androidTestImplementation('androidx.test.espresso:espresso-core:3.1.0', {
      //  exclude group: 'com.android.support', module: 'support-annotations'
    //})
    implementation(libs.multidex.instrumentation)
    implementation(libs.multidex)
    implementation(libs.appcompat)
    implementation(libs.viewmodel.android)
    implementation(libs.viewmodel.ktx)
    implementation(libs.livedata.ktx)
    implementation(libs.ktx.serialization.json)
    constraints {
        implementation(libs.ktx.coroutines.android)
    }
    implementation(libs.recyclerview)
    implementation(libs.cardview)
    implementation(libs.palette.ktx)
    implementation(libs.legacy)
    implementation(libs.vectordrawable)
    implementation(libs.constraintlayout)
    implementation(libs.material)
    implementation(libs.gson)
    implementation(libs.rxpermissions)
    implementation(libs.javax.annotation)
    implementation(libs.bundles.glide)
    ksp(libs.glide.compiler)
    implementation(libs.photoview)
    implementation(projects.libCommon)
    implementation(projects.libCustomView)
    implementation(projects.libMediaPicker)
    implementation(projects.libVideoRecord)
    implementation(projects.libWheelPicker)
    implementation(projects.libZxing)
}
